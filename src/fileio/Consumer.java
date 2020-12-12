package fileio;

import contracts.ConsumerContract;
import contracts.Debt;

public class Consumer extends Common {

    private int monthlyIncome;
    private ConsumerContract contract;
    private Debt debt;

    public Consumer(int id, int buget, int monthlyIncome) {
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

    public void setContract(ConsumerContract contract) {
        this.contract = contract;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

    public void getSalary() {
        this.payDay(this.monthlyIncome);
    }
}
