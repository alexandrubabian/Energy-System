package contracts;

import fileio.Consumer;

public final class DistribContract extends Contract {

    private final int consumerId;

    private Consumer consumer;

    public DistribContract(final int consumerId, final int price, final int remainedContractMonths,
                           final Consumer consumer) {
        super(price, remainedContractMonths);
        this.consumerId = consumerId;
        this.consumer = consumer;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public int getConsumerId() {
        return consumerId;
    }
}
