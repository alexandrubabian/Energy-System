package fileio;

import contracts.DistribContract;

import java.util.ArrayList;
import java.util.List;

public class Distributor extends Common {

    private int contractLength;
    private int infrastructureCost;
    private int productionCost;
    private List<DistribContract> contracts;
    private List<Consumer> inDebt;
    private int monthlyPrice;
    private int profit;

    public Distributor(int id, int contractLength, int buget, int infrastructureCost,
                       int productionCost) {
        super(id, buget);
        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
        this.contracts = new ArrayList<>();
        this.monthlyPrice = 0;
        this.profit = (int) Math.round(Math.floor(0.2 * productionCost));
        this.inDebt = new ArrayList<>();
    }

    public List<DistribContract> getContracts() {
        return contracts;
    }

    public int getContractLength() {
        return contractLength;
    }

    public int getMonthlyPrice() {
        return monthlyPrice;
    }

    public int getProfit() {
        return profit;
    }

    public List<Consumer> getInDebt() {
        return inDebt;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setInfrastructureCost(int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
        this.profit = (int) Math.round(Math.floor(0.2 * productionCost));
    }

    public void setMonthlyPrice(int monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public void eraseAllContracts() {
        this.contracts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "contracts=" + contracts +
                '}';
    }
}
