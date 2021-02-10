package write;

import fileio.MonthlyStats;

import java.util.List;

public final class ToWriteProducer {

    private final int id;

    private final int maxDistributors;

    private final Double priceKW;

    private final String energyType;

    private final int energyPerDistributor;

    private final List<MonthlyStats> monthlyStats;

    public ToWriteProducer(final int id, final int maxDistributors, final Double priceKW,
                           final String energyType, final int energyPerDistributor,
                           final List<MonthlyStats> monthlyStats) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyType = energyType;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }

    public int getId() {
        return id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public Double getPriceKW() {
        return priceKW;
    }

    public String getEnergyType() {
        return energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }
}
