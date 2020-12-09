package fileio;

public class Distributor extends Common{

    private int contractLength;
    private int infrastructureCost;
    private int productionCost;

    public Distributor(int id, int contractLength, int buget, int infrastructureCost,
                       int productionCost) {
        super(id, buget);
        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

}
