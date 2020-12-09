package fileio;

public class CommonFactory {
    public Common getCommon(String commonType, int id, int buget, int monthlyIncome,
                            int contractLength, int infrastructureCost,
                            int productionCost) {
        //to extend in part 2 of the homework when there will be 2 types of consumers;
        if(commonType.equals("consumer")) {
            return new Consumer(id, buget, monthlyIncome);
        } else if(commonType.equals("distributor")) {
            return new Distributor(id, contractLength, buget, infrastructureCost, productionCost);
        }
        return null;
    }
}
