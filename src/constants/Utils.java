package constants;

import fileio.Producer;

import java.util.ArrayList;

public final class Utils {

    private Utils() {

    }

    /**
     * Method use to determine for each strategy of a distributor, what producers he will choose
     * @param sorted - list of all producers sorted depending on the strategy
     * @param energyNeededKW how much energy the specific distributor needs
     */
    public static ArrayList<Producer> toReturn(final ArrayList<Producer> sorted,
                                               final int energyNeededKW) {
        ArrayList<Producer> toReturn = new ArrayList<>();
        int iterator = 0;
        int energySummed = 0;
        while (energySummed < energyNeededKW) {
            if (sorted.get(iterator).getObservers().size() < sorted.get(iterator).
                    getMaxDistributors()) {
                toReturn.add(sorted.get(iterator));
                energySummed += sorted.get(iterator).getEnergyPerDistributor();
            }
            iterator++;
        }
        return toReturn;
    }
}
