package fileio;

import constants.Constants;
import contracts.DistribContract;

import java.util.ArrayList;
import java.util.List;

public final class Distributor extends Common {

    private int contractLength;
    private int infrastructureCost;
    private int productionCost;
    private List<DistribContract> contracts;
    private List<Consumer> inDebt;
    private int monthlyPrice;
    private int profit;

    public Distributor(final int id, final int contractLength, final int buget,
                       final int infrastructureCost, final int productionCost) {
        super(id, buget);
        this.contractLength = contractLength;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
        this.contracts = new ArrayList<>();
        this.monthlyPrice = 0;
        this.profit = (int) Math.round(Math.floor(Constants.PROCENTAGEPROFIT * productionCost));
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

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }
    /**
     * Change the production cost with the one from parameter
     *
     * @param productionCost new productionCost
     */
    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
        this.profit = (int) Math.round(Math.floor(Constants.PROCENTAGEPROFIT * productionCost));
    }

    public void setMonthlyPrice(final int monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
    /**
     * Delete all the contracts from the distributor
     */
    public void eraseAllContracts() {
        this.contracts = new ArrayList<>();
    }
}
