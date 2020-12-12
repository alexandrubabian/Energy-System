package contracts;

import fileio.Consumer;

public class DistribContract extends Contract {

    private final int consumerId;

    private Consumer consumer;

    public DistribContract(int consumerId, int price, int remainedContractMonths,
                           Consumer consumer) {
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
