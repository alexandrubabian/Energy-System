package actions;

import constants.Constants;
import contracts.ConsumerContract;
import contracts.ContractFactory;
import contracts.Debt;
import contracts.DistribContract;
import fileio.Consumer;
import fileio.DistributorChange;
import fileio.Distributor;
import fileio.Input;
import fileio.MonthlyStats;
import fileio.Producer;
import fileio.ProducerChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class Action {
    private Input input;

    public Action(final Input input) {
        this.input = input;
    }

    /**
     * Introduce the new consumers
     *
     * @param month of the changes
     */
    public void introduceNewMonthCustomers(final int month) {
        for (Consumer iterator : input.getMonthlyUpdates().get(month).getNewConsumers()) {
            input.getConsumers().add(iterator);
        }
    }
    /**
     * In each month there it might be changes to be done to the distributors
     * @param month of the changes
     */
    public void introduceMonthDistributorChanges(final int month) {
        Distributor distributor;
        for (DistributorChange iterator : input.getMonthlyUpdates().get(month).
                getDistributorChanges()) {
            distributor = input.getDistributors().get(iterator.getId());
            if (distributor != null) {
                distributor.setInfrastructureCost(iterator.getInfrastructureCost());
            }
        }
    }

    /**
     * For each month, if there is any changes at the producers, this method will use Observer DP
     * and prepare the changes that will come for the distributors. There distributors are returned
     * in a list by the function
     * @param month when the operations are being procesed
     * @return the list of distributors that have to find new producers
     */
    public ArrayList<Distributor> introduceMonthProducerChanges(final int month) {
        Producer producer;
        Set<Distributor> set = new LinkedHashSet<>();
        ArrayList<Distributor> distributors = new ArrayList<>();
        for (ProducerChange iterator : input.getMonthlyUpdates().get(month).getProducerChanges()) {
            producer = Input.getProducers().get(iterator.getId());
            if (producer != null) {
                producer.setEnergyPerDistributor(iterator.getEnergyPerDistributor(), distributors);
            }
        }
        set.addAll(distributors);
        distributors.clear();
        distributors.addAll(set);
        Collections.sort(distributors, new Comparator<Distributor>() {
            @Override
            public int compare(Distributor o1, Distributor o2) {
                return o1.getId() - o2.getId();
            }
        });
        return distributors;
    }

    /**
     * Sets the price in each month for every distributor and return the distributor with best
     * price in that month
     * Also set the monthly contract price
     * @return distributor with best price
     */
    public Distributor determineBestDistributor() {
        int price;
        int bestPrice = Integer.MAX_VALUE;
        Distributor bestDistributor = null;
        for (Distributor iterator : input.getDistributors()) {
            if (!iterator.getBankrupt()) {
                iterator.setProductionAndProfit();
                if (iterator.getContracts().size() == 0) {
                    price = iterator.getInfrastructureCost() + iterator.getProductionCost()
                            + iterator.getProfit();
                } else {
                    price = (int) (Math.round(Math.floor(
                            iterator.getInfrastructureCost() / iterator.getContracts().size()))
                            + iterator.getProductionCost() + iterator.getProfit());
                }
                iterator.setContractCost(price);
                if (price < bestPrice) {
                    bestPrice = price;
                    bestDistributor = iterator;
                }
            }
        }
        return bestDistributor;
    }

    /**
     * In each month there is a bestDistributor, which will be chosen by all the free
     * customers
     */
    public void chooseContract(final Distributor bestDistributor) {
        ContractFactory contractFactory = ContractFactory.getInstance();
        for (Consumer iterator : input.getConsumers()) {
            if (!iterator.getBankrupt() && (iterator.getContract() == null
                    || iterator.getContract().getRemainedContractMonths() == 0)) {
                iterator.setContract((ConsumerContract) contractFactory.createContract(
                        "consumer",
                        bestDistributor.getContractCost(), bestDistributor.getContractLength(),
                        bestDistributor.getId(), bestDistributor));


                bestDistributor.getContracts().add((DistribContract) contractFactory.createContract(
                        "distributor", bestDistributor.getContractCost(),
                        bestDistributor.getContractLength(), iterator.getId(), iterator));
            }
        }
    }

    /**
     * It removes the contracts that have 0 months left
     */
    public void cleanUpExpiredContracts() {
        for (Distributor iterator : input.getDistributors()) {
            iterator.getContracts().removeIf(x -> x.getRemainedContractMonths() == 0);
        }
    }

    /**
     * Each consumer get his monthly salary if he is not bankrupt
     */
    public void receiveSalary() {
        for (Consumer iterator : input.getConsumers()) {
            if (!iterator.getBankrupt()) {
                iterator.getSalary();
            }
        }
    }
    /**
     * Each consumer pays his monthly tax, it he has a debt we look if it can pay both of them
     * if not, we declare him bankrupt. If he can pay the monthly tax, we populate the field
     * debt of the consumer. If the consumer won't be able to pay the taxes both months, the
     * distributor won't get the money
     */
    public void pay() {
        Distributor distributor;
        for (Consumer iterator : input.getConsumers()) {
            if (!iterator.getBankrupt()) {
                distributor = iterator.getContract().getDistributor();
                //if he has no debt
                if (iterator.getDebt() == null) {
                    if (iterator.getBuget() >= iterator.getContract().getPrice()) {
                        iterator.pay(iterator.getContract().getPrice());
                        distributor.setBuget(
                                iterator.getContract().getPrice() + distributor.getBuget());
                    } else {
                        //he has no debt but he can't pay the actual contract
                        iterator.setDebt(new Debt((int) (Constants.PROCENTAGEDEBT
                                * iterator.getContract().getPrice()), distributor));
                        distributor.getInDebt().add(iterator);
                    }
                } else {
                    //he has debt

                    //same debt distributor as the actual distributor
                    if (distributor.equals(iterator.getDebt().getDistributorToPay())) {
                        //if he can pay the debt and the price
                        if (iterator.getBuget() >= (iterator.getContract().getPrice()
                                + iterator.getDebt().getMoneyToPay())) {

                            iterator.pay(iterator.getContract().getPrice()
                                    + iterator.getDebt().getMoneyToPay());

                            distributor.setBuget(iterator.getContract().getPrice()
                                    + distributor.getBuget());
                            iterator.getDebt().getDistributorToPay().setBuget(
                                    iterator.getDebt().getMoneyToPay()
                                            + iterator.getDebt().getDistributorToPay()
                                            .getBuget());
                            //no more in debt
                            iterator.getDebt().getDistributorToPay().getInDebt()
                                    .remove(iterator);
                            iterator.setDebt(null);
                        } else {
                            iterator.setBankrupt(true);
                            iterator.getContract().getDistributor().getContracts()
                                    .removeIf(x -> x.getConsumer().equals(iterator));
                            iterator.getDebt().getDistributorToPay().getInDebt().remove(iterator);
                        }
                    } else {
                        //if the distributor where he has debt is not the same as the actual one

                        //if he can pay the debt to the other distributor
                        if (iterator.getBuget() >= (iterator.getDebt().getMoneyToPay())) {
                            iterator.pay(iterator.getDebt().getMoneyToPay());
                            iterator.getDebt().getDistributorToPay().setBuget(
                                    iterator.getDebt().getMoneyToPay()
                                            + iterator.getDebt().getDistributorToPay()
                                            .getBuget());

                            //no more in debt
                            iterator.getDebt().getDistributorToPay().getInDebt()
                                    .remove(iterator);
                            iterator.setDebt(new Debt((int) (Constants.PROCENTAGEDEBT
                                    * iterator.getContract().getPrice()), distributor));
                        } else {
                            //he can't pay his debt to the other distributor
                            iterator.setBankrupt(true);
                            iterator.getContract().getDistributor().getContracts()
                                    .removeIf(x -> x.getConsumer().equals(iterator));
                            iterator.getDebt().getDistributorToPay().getInDebt()
                                    .remove(iterator);
                        }
                    }
                }
            }
        }
    }

    /**
     * Find the distributor with the budget <0 and it makes it bankrupt
     */
    public void distributorBankrupt() {
        for (Distributor iterator : input.getDistributors()) {
            if (!iterator.getBankrupt()) {
                if (iterator.getBuget() < 0) {
                    iterator.setBankrupt(true);
                    //parcurg toti utilizatorii si le anulez contractul
                    this.deleteContracts(iterator);
                    iterator.eraseAllContracts();
                }
            }
        }
    }

    /**
     * For the distributor from the parameter, the function will delete all hist cotracts
     *
     * @param distributor to be deleted contracts
     */
    public void deleteContracts(final Distributor distributor) {
        for (Consumer iterator : distributor.getInDebt()) {
            iterator.setDebt(null);
        }
        for (DistribContract iterator : distributor.getContracts()) {
            iterator.getConsumer().setContract(null);
        }
    }
    /**
     * Each distributor that is not bankrupt will have to pay his taxes
     */
    public void distributorPay() {
        int payment;
        for (Distributor iterator : input.getDistributors()) {
            if (!iterator.getBankrupt()) {
                payment = iterator.getInfrastructureCost() + iterator.getProductionCost()
                        * iterator.getContracts().size();
                iterator.pay(payment);
            }
        }
    }

    /**
     * Decrease with 1 the number of months of each contract
     */
    public void decreaseConstractMonths() {
        for (Distributor iterator : input.getDistributors()) {
            for (DistribContract contractIterator : iterator.getContracts()) {
                contractIterator.decreaseMonths();
                contractIterator.getConsumer().getContract().decreaseMonths();
            }
        }
    }

    /**
     * The method for the initial round, when all the distributors have to find their first
     * producers depending on their strategy
     */
    public void chooseFirstProducers() {
        ArrayList<Producer> futureProducers;
        for (Distributor iterator : input.getDistributors()) {
            futureProducers =  new ArrayList<>(iterator.getStrategy().doOperation((
                    ArrayList<Producer>) Input.getProducers(), iterator.getEnergyNeededKW()));

            for (Producer producer : futureProducers) {
                iterator.addSubject(producer);
            }
        }
    }
    /**
     * Instantiate the field for a specific month after all the operation are being processed
     * @param month when it need to be added the stats of the producers
     */
    public void setMonthlyStats(final int month) {
        ArrayList<Integer> distributorIds;
        for (Producer iterator : Input.getProducers()) {
            distributorIds = new ArrayList<>();
            Collections.sort(iterator.getObservers(), new Comparator<Distributor>() {
                @Override
                public int compare(Distributor o1, Distributor o2) {
                    return o1.getId() - o2.getId();
                }
            });
            for (Distributor distributor : iterator.getObservers()) {
                distributorIds.add(distributor.getId());
            }
            iterator.getMonthlyStats().add(new MonthlyStats(month + 1, distributorIds));
        }
    }

    /**
     * Update the producers of the distributors that are given as a parameter
     * @param distributors list of distributors that has to find others producers
     */
    public void changeDistributors(final ArrayList<Distributor> distributors) {
        for (Distributor iterator : distributors) {
            iterator.update();
        }
    }
}
