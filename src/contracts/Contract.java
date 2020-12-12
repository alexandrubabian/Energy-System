package contracts;

public abstract class Contract {

    private int price;

    private int remainedContractMonths;

    public Contract() {
    }

    public Contract(final int price, final int remainedContractMonths) {
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }
    /**
     * Getter for price
     * @return the price
     */
    public int getPrice() {
        return price;
    }
    /**
     * Getter for remainedContractMonths
     * @return the remainedContractMonths
     */
    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
    /**
     * Setter for price
     *
     * @param price of the contract
     */
    public void setPrice(final int price) {
        this.price = price;
    }
    /**
     * Setter for remainedContractMonths
     * @param remainedContractMonths of the contract
     */
    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }
    /**
     * Decrease with 1 the number of months
     */
    public void decreaseMonths() {
        this.remainedContractMonths--;
    }
}
