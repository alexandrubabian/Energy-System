package fileio;

public class Consumer extends Common{

    private int monthlyIncome;

    public Consumer(int id, int buget, int monthlyIncome) {
        super(id, buget);
        this.monthlyIncome = monthlyIncome;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

}
