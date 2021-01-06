package fileio;

import entities.EnergyType;

import java.util.ArrayList;
import java.util.List;

public final class Producer extends Common {

    private EnergyType energyType;
    private int maxDistributors;
    private Double priceKW;
    private int energyPerDistributor;
    private ArrayList<Distributor> observers = new ArrayList<>();
    private List<MonthlyStats> monthlyStats;

    public Producer(final int id, final String energyType, final int maxDistributors,
                    final Double priceKW, final int energyPerDistributor) {
        super(id);
        this.energyType = EnergyType.valueOf(energyType);
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = new ArrayList<>();
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(final int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }


    public Double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(final Double priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public ArrayList<Distributor> getObservers() {
        return observers;
    }

    /**
     * The observer method
     * @param energyDistributor of the producer
     * @param distributors list of distributors that has to find others producers
     */
    public void setEnergyPerDistributor(final int energyDistributor,
                                        final ArrayList<Distributor> distributors) {
        this.energyPerDistributor = energyDistributor;
        notifyAllObservers(distributors);
    }

    /**
     * For each change of a producer, it has to be created the list with all the distributors
     * affected
     * @param distributors list of distributors that has to find others producers
     */
    private void notifyAllObservers(final ArrayList<Distributor> distributors) {
        distributors.addAll(this.getObservers());
    }

    /**
     * Attach a distributor to the list of observers of a producer
     * @param distributor who has to be added
     */
    public void attach(final Distributor distributor) {
        observers.add(distributor);
    }
}
