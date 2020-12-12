package constants;

import fileio.Distributor;
import java.util.List;

public final class Utils {
    private Utils() {
    }

    /**
     * Find the distributor with the id from paramter
     *
     * @param id of distributor to look for
     * @param distributors list of distributors
     * @return the distributor
     */
    public static Distributor findDistributor(final int id,
                                              final List<Distributor> distributors) {
        for (Distributor iterator : distributors) {
            if (iterator.getId() == id) {
                return iterator;
            }
        }
        return null;
    }
}
