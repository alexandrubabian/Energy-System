package strategies;

import fileio.Producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PriceStrategy implements Strategy{

    @Override
    public ArrayList<Producer> doOperation(ArrayList<Producer> producers, int energyNeededKW) {
        ArrayList<Producer> toReturn = new ArrayList<>();
        ArrayList<Producer> toSort = new ArrayList<>(producers);
        int energySummed = 0;
        Collections.sort(toSort, new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                return Double.compare(o1.getPriceKW(), o2.getPriceKW());
            }
        }.thenComparing(new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                return o1.getEnergyPerDistributor() - o2.getEnergyPerDistributor();
            }
        }).thenComparing(new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                return o1.getId() - o2.getId();
            }
        }));

        int iterator = 0;
        while (energySummed < energyNeededKW) {
            if(toSort.get(iterator).getObservers().size() < toSort.get(iterator).getMaxDistributors()) {
                toReturn.add(toSort.get(iterator));
                energySummed += toReturn.get(iterator).getEnergyPerDistributor();
            }
            iterator++;
        }
        return toReturn;
    }
}
