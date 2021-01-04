package fileio;

import java.util.ArrayList;

public class MonthlyStats {
    private int month;
    private ArrayList<Integer> distributorIds;

    public MonthlyStats(int month, ArrayList<Integer> distributorIds) {
        this.month = month;
        this.distributorIds = distributorIds;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public ArrayList<Integer> getDistributorIds() {
        return distributorIds;
    }

    public void setDistributorIds(ArrayList<Integer> distributorIds) {
        this.distributorIds = distributorIds;
    }
}
