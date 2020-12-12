package write;

import java.util.ArrayList;
import java.util.List;

public final class ToWriteDistributor {

    private final int id;

    private final int budget;

    private final Boolean isBankrupt;

    private final List<ToWriteContracts> contracts;

    public ToWriteDistributor(final int id, final int budget, final Boolean isBankrupt) {
        this.id = id;
        this.budget = budget;
        this.isBankrupt = isBankrupt;
        this.contracts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getBudget() {
        return budget;
    }

    public Boolean getIsBankrupt() {
        return isBankrupt;
    }

    public List<ToWriteContracts> getContracts() {
        return contracts;
    }
}
