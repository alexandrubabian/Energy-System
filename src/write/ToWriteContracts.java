package write;

public final class ToWriteContracts {

    private final int consumerId;

    private final int price;

    private final int remainedContractMonths;

    public ToWriteContracts(final int consumerId, final int price,
                            final int remainedContractMonths) {
        this.consumerId = consumerId;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public int getPrice() {
        return price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }
}
