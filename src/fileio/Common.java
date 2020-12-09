package fileio;

public abstract class Common {
    private final int id ;
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
}