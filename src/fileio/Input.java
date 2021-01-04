package fileio;

import java.util.List;

public final class Input {
    private final List<Consumer> consumers;

    private final List<Distributor> distributors;

    private static List<Producer> producers;

    private final List<MonthlyUpdate> monthlyUpdates;

    public Input(final List<Consumer> consumers, final List<Distributor> distributors,
                 final List<MonthlyUpdate> monthlyUpdates, final List<Producer> producers) {
        this.consumers = consumers;
        this.distributors = distributors;
        Input.producers = producers;
        this.monthlyUpdates = monthlyUpdates;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public static List<Producer> getProducers() {
        return producers;
    }
}
