package fileio;

import java.util.ArrayList;

public final class MonthlyStats {
    private int month;
    private ArrayList<Integer> distributorsIds;

    public MonthlyStats(final int month, final ArrayList<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public ArrayList<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(final ArrayList<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
