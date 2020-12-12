package fileio;

public abstract class Common {
    private final int id;
    private int buget;
    private Boolean isBankrupt;

    public Common(int id, int buget) {
        this.id = id;
        this.buget = buget;
        this.isBankrupt = false;
    }

    public int getId() {
        return id;
    }

    public int getBuget() {
        return buget;
    }

    public Boolean getBankrupt() {
        return isBankrupt;
    }

    public void payDay(int salary) {
        this.buget += salary;
    }

    public void setBuget(int buget) {
        this.buget = buget;
    }

    public void setBankrupt(Boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public void pay(int payment) {
        this.buget -= payment;
    }

    @Override
    public String toString() {
        return "Common{" +
                "id=" + id +
                ", buget=" + buget +
                ", isBankrupt=" + isBankrupt +
                '}';
    }
}
