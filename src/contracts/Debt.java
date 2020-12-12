package contracts;

import fileio.Distributor;

public class Debt {

    private int moneyToPay;

    private Distributor distributorToPay;

    public Debt(int moneyToPay, Distributor distributorToPay) {
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

