package fileio;

import contracts.ConsumerContract;
import contracts.Debt;

public final class Consumer extends Common {

    private int monthlyIncome;
    private ConsumerContract contract;
    private Debt debt;

    public Consumer(final int id, final int buget, final int monthlyIncome) {
        super(id, buget);
        this.monthlyIncome = monthlyIncome;
        this.contract = null;
        this.debt = null;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public ConsumerContract getContract() {
        return contract;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setContract(final ConsumerContract contract) {
        this.contract = contract;
    }

    public void setDebt(final Debt debt) {
        this.debt = debt;
    }

    /**
     * Increase the budget with the monthlyIncome
     */
    public void getSalary() {
        this.payDay(this.monthlyIncome);
    }
}
