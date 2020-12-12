package fileio;

public abstract class Common {
    private final int id;
    private int buget;
    private Boolean isBankrupt;

    public Common(final int id, final int buget) {
        this.id = id;
        this.buget = buget;
        this.isBankrupt = false;
    }
    /**
     * Getter for the id
     * @return the id of the object
     */
    public int getId() {
        return id;
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
     * Increase the budget
     * @param salary of the consumer
     */
    public void payDay(final int salary) {
        this.buget += salary;
    }
    /**
     * Sets the budget
     * @param buget of video
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
     * Reduce the budget with number payment
     * @param payment of object
     */
    public void pay(final int payment) {
        this.buget -= payment;
    }
}
