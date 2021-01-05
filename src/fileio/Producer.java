package fileio;

import entities.EnergyType;
import strategies.Strategy;
import strategies.StrategyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Producer extends Common{

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

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }


    public Double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(Double priceKW) {
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
     * @param energyPerDistributor of the producer
     */
    public void setEnergyPerDistributor(int energyPerDistributor, ArrayList<Distributor> distributors) {
        this.energyPerDistributor = energyPerDistributor;
        notifyAllObservers(distributors);
        //TODO delete all the distributors addicted to this producer
    }

    private void notifyAllObservers(ArrayList<Distributor> distributors) {
//        ArrayList<Distributor> copyOfObservers = new ArrayList<>(observers);
////        for(Distributor iterator : copyOfObservers) {
////            iterator.getSubjects().remove(this);
////        }
//        this.observers = new ArrayList<>();
        distributors.addAll(this.getObservers());
//        Collections.sort(copyOfObservers, new Comparator<Distributor>() {
//            @Override
//            public int compare(Distributor o1, Distributor o2) {
//                return o1.getId() - o2.getId();
//            }
//        });
//        for (Distributor iterator : copyOfObservers) {
//
//            //TODO check if true
//            if(!iterator.getBankrupt()) {
//                iterator.update();
//            }
//        }
    }

    public void attach(Distributor distributor) {
        observers.add(distributor);
    }
}
