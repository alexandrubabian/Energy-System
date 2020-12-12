package constants;

import fileio.Distributor;
import java.util.List;

public final class Utils {
    private Utils() {
    }

    /**
     * finding the specific distributor by his id
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
