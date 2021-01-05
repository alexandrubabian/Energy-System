package fileio;

import constants.Constants;
import contracts.DistribContract;
import strategies.EnergyChoiceStrategyType;
import strategies.Strategy;
import strategies.StrategyFactory;

import java.util.ArrayList;
import java.util.List;

public final class Distributor extends Common {

    private int contractLength;
    private int infrastructureCost;
    private int buget;
    private Boolean isBankrupt;
    private int productionCost;
    private int energyNeededKW;
    private EnergyChoiceStrategyType producerStrategy;
    private List<DistribContract> contracts;
    private List<Consumer> inDebt;
    private int contractCost;
    private int profit;
    private Strategy strategy;
    private List<Producer> subjects = new ArrayList<>();

    public Distributor(final int id, final int contractLength, final int buget,
                       final int infrastructureCost, final int energyNeededKW,
                       final String producerStrategy) {
        super(id);
        this.buget = buget;
        this.isBankrupt = false;
        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.energyNeededKW = energyNeededKW;
        this.producerStrategy = EnergyChoiceStrategyType.valueOf(producerStrategy);
        this.strategy = StrategyFactory.getInstance().getStrategy(this.producerStrategy);
        this.productionCost = 0;
        this.contractCost = 0;
        this.contracts = new ArrayList<>();
        this.profit = 0;
        this.inDebt = new ArrayList<>();
    }

    public List<DistribContract> getContracts() {
        return contracts;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getContractCost() {
        return contractCost;
    }

    public int getProfit() {
        return profit;
    }

    /**
     * Getter for budget
     * @return the budget
     */
    public int getBuget() {
        return buget;
    }

    /**
     * Return if the object is bankrupt
     * @return true or false
     */
    public Boolean getBankrupt() {
        return isBankrupt;
    }

    /**
     * Sets the budget
     * @param buget of video
     */
    public void setBuget(final int buget) {
        this.buget = buget;
    }

    /**
     * Set the bankrupt statement
     * @param bankrupt of object
     */
    public void setBankrupt(final Boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public List<Consumer> getInDebt() {
        return inDebt;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public List<Producer> getSubjects() {
        return subjects;
    }

    public void setProfit() {
        this.profit = (int) Math.round(Math.floor(Constants.PROCENTAGEPROFIT * productionCost));
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
    /**
     * Change the production cost with the one calculated
     * Set the profit also
     *
     */
    public void setProductionAndProfit() {
        //this.productionCost = productionCost;
        Double cost = 0.0;
        for(Producer iterator : this.subjects) {
            //TODO verifica daca trebuie Math.floor
            cost += (iterator.getEnergyPerDistributor() * iterator.getPriceKW());
        }
        this.productionCost = (int) Math.round(Math.floor(cost / 10));
        this.setProfit();
    }

    public void setContractCost(final int contractCost) {
        this.contractCost = contractCost;
    }
    /**
     * Delete all the contracts from the distributor
     */
    public void eraseAllContracts() {
        this.contracts = new ArrayList<>();
    }

    /**
     * Reduce the budget with number payment
     * @param payment of object
     */
    public void pay(final int payment) {
        this.buget -= payment;
    }

    /**
     * Do the connections
     * @param producer to be added to the list subjects and to also add the distributor himself,
     *                 to the producers observers list
     */
    public void addSubject(Producer producer) {
        this.subjects.add(producer);
        producer.attach(this);
    }
    /**
     * This method is for a distributor, first, it removes himself from all the observers List
     * of any producer that contain it. Then, in futureProducers, there will be all the new
     * producers resulted from the specific strategy of the distributor and it creates the links
     * via parameter subjects, between the distributor and his nnew producers
     */
    public void update() {
        for(Producer iterator : this.getSubjects()) {
            iterator.getObservers().remove(this);
        }
        ArrayList<Producer> futureProducers = strategy.doOperation((ArrayList<Producer>)
                        Input.getProducers(), energyNeededKW);

        this.subjects = new ArrayList<>();
        for(Producer iterator : futureProducers) {
            this.addSubject(iterator);
        }
    }
}
