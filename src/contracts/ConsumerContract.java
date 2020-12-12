package contracts;

import fileio.Distributor;

public class ConsumerContract extends Contract {

    private Distributor distributor;


    public ConsumerContract() {
        super();
    }

    public ConsumerContract(Distributor distributor, int price, int remainedContractMonths) {
        super(price, remainedContractMonths);
        this.distributor = distributor;
    }

    public Distributor getDistributor() {
        return distributor;
    }
}
