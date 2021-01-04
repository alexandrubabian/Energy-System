package fileio;

import java.util.List;

public final class MonthlyUpdate {

    private final List<Consumer> newConsumers;

    private final List<DistributorChange> distributorChanges;

    private final List<ProducerChange> producerChanges;

    public MonthlyUpdate(final List<Consumer> newConsumers,
                         final List<DistributorChange> distributorChanges,
                         final List<ProducerChange> producerChanges) {
        this.newConsumers = newConsumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public List<DistributorChange> getDistributorChanges() {
        return distributorChanges;
    }

    public List<ProducerChange> getProducerChanges() {
        return producerChanges;
    }
}
