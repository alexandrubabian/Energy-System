package contracts;

import fileio.Distributor;

public final class ConsumerContract extends Contract {

    private Distributor distributor;

    public ConsumerContract() {
        super();
    }

    public ConsumerContract(final Distributor distributor, final int price,
                            final int remainedContractMonths) {
        super(price, remainedContractMonths);
        this.distributor = distributor;
    }

    public Distributor getDistributor() {
        return distributor;
    }
}
