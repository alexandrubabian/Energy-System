package write;

public final class ToWriteConsumer {

    private final int id;

    private final Boolean isBankrupt;

    private final int budget;

    public ToWriteConsumer(final int id, final Boolean isBankrupt, final int budget) {
        this.id = id;
        this.isBankrupt = isBankrupt;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public Boolean getIsBankrupt() {
        return isBankrupt;
    }

    public int getBudget() {
        return budget;
    }
}
