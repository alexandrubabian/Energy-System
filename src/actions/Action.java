package actions;

import constants.Utils;
import contracts.ConsumerContract;
import contracts.Contract;
import contracts.ContractFactory;
import contracts.Debt;
import contracts.DistribContract;
import fileio.Consumer;
import fileio.CostChange;
import fileio.Distributor;
import fileio.Input;

import java.util.ArrayList;

public class Action {
    private Input input;

    public Action(final Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
    }

    public void introduceNewMonthCustomers(int month) {
        for (Consumer iterator : input.getMonthlyUpdates().get(month).getNewConsumers()) {
            input.getConsumers().add(iterator);
        }
    }

    public void introduceMonthCostChanges(int month) {
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
    public void chooseContract(Distributor bestDistributor) {
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
     * la fiecare inceput de luna trebuie eliminati din lista unui distribuitor
     * contractele care au ajuns la final adica cele care au 0 luni ramase
     */
    public void cleanUpExpiredContracts() {
        //TODO vezi daca e vreun caz special de afisare atunci cand unul apare la datorii
        for (Distributor iterator : input.getDistributors()) {
            iterator.getContracts().removeIf(x -> x.getRemainedContractMonths() == 0);
        }
    }

    /**
     * ai luat decizia ca indiferent daca un consumator deja a iesit din joc
     * tot sa ii cresti venitul lunar
     */
    public void receiveSalary() {
        for (Consumer iterator : input.getConsumers()) {
            if (!iterator.getBankrupt()) {
                iterator.getSalary();
            }
        }
    }

    /**
     * Fiecare consumator isi plateste taxa lunara. Daca are si o datorie, se cauta daca le poate
     * plati pe amandoua, daca nu poate, va intra in faliment. Daca nu isi poate plati factura
     * curenta si are si datorie, va intra in faliment. Daca nu isi poate plati fact curenta
     * dar nu are nicio datorie va intra in datorii. De fiecare data cand un consumator intra
     * in faliment il sterg din lista de consumatori datori ai distribuitorului la care era datoria
     */
    public void pay() {
        Distributor distributor;
        for (Consumer iterator : input.getConsumers()) {
            if (!iterator.getBankrupt()) {
                distributor = iterator.getContract().getDistributor();
                if (iterator.getBuget() >= iterator.getContract().getPrice()) {
                    //nu are datorie si o poate plati
                    if (iterator.getDebt() == null) {
                        iterator.pay(iterator.getContract().getPrice());
                        distributor.setBuget(
                                iterator.getContract().getPrice() + distributor.getBuget());

                    } else {
                        //cazul in care are datorie si de asemenea si-o poate plati
                        if (iterator.getBuget() >= (iterator.getContract().getPrice()
                                + iterator.getDebt().getMoneyToPay())) {

                            iterator.pay(iterator.getContract().getPrice()
                                    + iterator.getDebt().getMoneyToPay());

                            distributor.setBuget(iterator.getContract().getPrice()
                                    + distributor.getBuget());
                            iterator.getDebt().getDistributorToPay().setBuget(
                                    iterator.getDebt().getMoneyToPay()
                                            + iterator.getDebt().getDistributorToPay().getBuget());
                            //a scapat de datorii
                            iterator.getDebt().getDistributorToPay().getInDebt().remove(iterator);
                            iterator.setDebt(null);

                        } else {
                            //nu poate plati si datoria si factura curenta
                            iterator.setBankrupt(true);
                            iterator.getContract().getDistributor().getContracts()
                                    .removeIf(x -> x.getConsumer().equals(iterator));
                            iterator.getDebt().getDistributorToPay().getInDebt().remove(iterator);
                        }
                    }

                } else {
                    //nu-si poate plati factura curenta

                    //daca nu are datorii
                    if (iterator.getDebt() == null) {
                        iterator.setDebt(new Debt((int) (1.2 * iterator.getContract().getPrice()),
                                distributor));
                        distributor.getInDebt().add(iterator);
                    } else {
                        iterator.setBankrupt(true);
                        iterator.getContract().getDistributor().getContracts()
                                .removeIf(x -> x.getConsumer().equals(iterator));
                        iterator.getDebt().getDistributorToPay().getInDebt().remove(iterator);
                    }
                }
            }
        }
    }

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

    public void deleteContracts(Distributor distributor) {

        for (Consumer iterator : distributor.getInDebt()) {
            iterator.setDebt(null);
        }
        //sterg toate contractele consumatorilor care aveau contract la distribuitorul ce a intrat
        //in faliment
        for (DistribContract iterator : distributor.getContracts()) {
            iterator.getConsumer().setContract(null);
        }
    }

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

    //mai trece o luna peste narnia
    public void decreaseConstractMonths() {
        for (Distributor iterator : input.getDistributors()) {
            for (DistribContract contractIterator : iterator.getContracts()) {
                contractIterator.decreaseMonths();
                contractIterator.getConsumer().getContract().decreaseMonths();
            }
        }
    }
}
