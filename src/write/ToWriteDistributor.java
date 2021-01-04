package write;

import java.util.ArrayList;
import java.util.List;

public final class ToWriteDistributor {

    private final int id;

    private final int energyNeededKW;

    private final int contractCost;

    private final int budget;

    private final String producerStrategy;

    private final Boolean isBankrupt;

    private final List<ToWriteContracts> contracts;

    public ToWriteDistributor(final int id, int energyNeededKW, int contractCost, final int budget,
                              String producerStrategy, final Boolean isBankrupt) {
        this.id = id;
        this.energyNeededKW = energyNeededKW;
        this.contractCost = contractCost;
        this.budget = budget;
        this.producerStrategy = producerStrategy;
        this.isBankrupt = isBankrupt;
        this.contracts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
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
