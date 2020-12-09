package fileio;

public class CostChange {

    private final int id;

    private final int infrastructureCost;

    private final int productionCost;

    public CostChange(int id, int infrastructureCost, int productionCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
    }

    public int getId() {
        return id;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }
}
