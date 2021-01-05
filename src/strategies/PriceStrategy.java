package strategies;

import constants.Utils;
import fileio.Producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PriceStrategy implements Strategy{

    @Override
    public ArrayList<Producer> doOperation(final ArrayList<Producer> producers,
                                           final int energyNeededKW) {
        ArrayList<Producer> toSort = new ArrayList<>(producers);
        Collections.sort(toSort, new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                return Double.compare(o1.getPriceKW(), o2.getPriceKW());
            }
        }.thenComparing(new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                return o2.getEnergyPerDistributor() - o1.getEnergyPerDistributor();
            }
        }).thenComparing(new Comparator<Producer>() {
            @Override
            public int compare(Producer o1, Producer o2) {
                return o1.getId() - o2.getId();
            }
        }));

        return Utils.toReturn(toSort, energyNeededKW);
    }
}
