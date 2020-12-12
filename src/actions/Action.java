package actions;

import constants.Constants;
import constants.Utils;
import contracts.ConsumerContract;
import contracts.ContractFactory;
import contracts.Debt;
import contracts.DistribContract;
import fileio.Consumer;
import fileio.CostChange;
import fileio.Distributor;
import fileio.Input;

public final class Action {
    private Input input;

    public Action(final Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
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
    public void introduceMonthCostChanges(final int month) {
        Distributor distributor;
        for (CostChange iterator : input.getMonthlyUpdates().get(month).getCostChanges()) {
            distributor = Utils.findDistributor(iterator.getId(), input.getDistributors());
            distributor.setInfrastructureCost(iterator.getInfrastructureCost());
            distributor.setProductionCost(iterator.getProductionCost());
        }
    }

    /**
     * Sets the price in each month for every distributor and return the distributor with best
     * price in that month
     *
     * @return distributor with best price
     */
    public Distributor determineBestDistributor() {
        int price;
        int bestPrice = Integer.MAX_VALUE;
        Distributor bestDistributor = null;
        for (Distributor iterator : input.getDistributors()) {
            if (!iterator.getBankrupt()) {
                if (iterator.getContracts().size() == 0) {
                    price = iterator.getInfrastructureCost() + iterator.getProductionCost()
                            + iterator.getProfit();
                } else {
                    price = (int) (Math.round(Math.floor(
                            iterator.getInfrastructureCost() / iterator.getContracts().size()))
                            + iterator.getProductionCost() + iterator.getProfit());
                }
                iterator.setMonthlyPrice(price);
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
                        bestDistributor.getMonthlyPrice(), bestDistributor.getContractLength(),
                        bestDistributor.getId(), bestDistributor));


                bestDistributor.getContracts().add((DistribContract) contractFactory.createContract(
                        "distributor", bestDistributor.getMonthlyPrice(),
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
                if (iterator.getBuget() >= iterator.getContract().getPrice()) {
                    //no debt and he can also pay it
                    if (iterator.getDebt() == null) {
                        iterator.pay(iterator.getContract().getPrice());
                        distributor.setBuget(
                                iterator.getContract().getPrice() + distributor.getBuget());

                    } else {
                        //if he has debt and he can pay the debt and the actual price
                        if (iterator.getBuget() >= (iterator.getContract().getPrice()
                                + iterator.getDebt().getMoneyToPay())) {

                            iterator.pay(iterator.getContract().getPrice()
                                    + iterator.getDebt().getMoneyToPay());

                            distributor.setBuget(iterator.getContract().getPrice()
                                    + distributor.getBuget());
                            iterator.getDebt().getDistributorToPay().setBuget(
                                    iterator.getDebt().getMoneyToPay()
                                            + iterator.getDebt().getDistributorToPay().getBuget());
                            //no more in debt
                            iterator.getDebt().getDistributorToPay().getInDebt().remove(iterator);
                            iterator.setDebt(null);

                        } else {
                            //he can't pay the second bill also
                            iterator.setBankrupt(true);
                            iterator.getContract().getDistributor().getContracts()
                                    .removeIf(x -> x.getConsumer().equals(iterator));
                            iterator.getDebt().getDistributorToPay().getInDebt().remove(iterator);
                        }
                    }

                } else {
                    //The consumer can't pay the bill
                    //case1: no debt
                    if (iterator.getDebt() == null) {
                        iterator.setDebt(new Debt((int) (Constants.PROCENTAGEDEBT
                                * iterator.getContract().getPrice()), distributor));
                        distributor.getInDebt().add(iterator);
                    } else {
                        //case2: he already has a bill to pay
                        iterator.setBankrupt(true);
                        iterator.getContract().getDistributor().getContracts()
                                .removeIf(x -> x.getConsumer().equals(iterator));
                        iterator.getDebt().getDistributorToPay().getInDebt().remove(iterator);
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
}
