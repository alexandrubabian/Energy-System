package contracts;

public abstract class Contract {

    private int price;

    private int remainedContractMonths;

    public Contract() {
    }

    public Contract(int price, int remainedContractMonths) {
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getPrice() {
        return price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRemainedContractMonths(int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    public void decreaseMonths() {
        this.remainedContractMonths--;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "price=" + price +
                ", remainedContractMonths=" + remainedContractMonths +
                '}';
    }
}
