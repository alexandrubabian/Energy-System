package fileio;

public final class DistributorChange {

    private final int id;

    private final int infrastructureCost;

    public DistributorChange(final int id, final int infrastructureCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
    }

    public int getId() {
        return id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

}
