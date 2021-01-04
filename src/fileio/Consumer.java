package fileio;

import contracts.ConsumerContract;
import contracts.Debt;

public final class Consumer extends Common {

    private int monthlyIncome;
    private int buget;
    private Boolean isBankrupt;
    private ConsumerContract contract;
    private Debt debt;

    public Consumer(final int id, final int buget, final int monthlyIncome) {
        super(id);
        this.buget = buget;
        this.isBankrupt = false;
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
     * Getter for budget
     * @return the budget
     */
    public int getBuget() {
        return buget;
    }

    /**
     * Return if the object is bankrupt
     * @return true or false
     */
    public Boolean getBankrupt() {
        return isBankrupt;
    }

    /**
     * Sets the budget
     * @param buget of object
     */
    public void setBuget(final int buget) {
        this.buget = buget;
    }

    /**
     * Set the bankrupt statement
     * @param bankrupt of object
     */
    public void setBankrupt(final Boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    /**
     * Increase the budget with the monthlyIncome
     */
    public void getSalary() {
        buget += monthlyIncome;
    }

    /**
     * Reduce the budget with number payment
     * @param payment of object
     */
    public void pay(final int payment) {
        this.buget -= payment;
    }
}
