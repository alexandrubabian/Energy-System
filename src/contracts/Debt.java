package contracts;

import fileio.Distributor;

public final class Debt {

    private int moneyToPay;

    private Distributor distributorToPay;

    public Debt(final int moneyToPay, final Distributor distributorToPay) {
        this.moneyToPay = moneyToPay;
        this.distributorToPay = distributorToPay;
    }

    public int getMoneyToPay() {
        return moneyToPay;
    }

    public Distributor getDistributorToPay() {
        return distributorToPay;
    }


}

