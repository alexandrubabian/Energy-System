package fileio;

public final class CommonFactory {

    private static CommonFactory instance;

    private CommonFactory() {
    }
    /**
     * Singleton method
     * @return the instance of the class
     */
    public static CommonFactory getInstance() {
        if (instance == null) {
            instance = new CommonFactory();
        }
        return instance;
    }

    /**
     * Return the object from abstract class Common
     *
     * @param id
     * @param buget
     * @param commonType
     * @param contractLength
     * @param infrastructureCost
     * @param monthlyIncome
     * @param productionCost
     * @return new Consumer or new Distributor
     */
    public Common getCommon(final String commonType, final int id, final int buget,
                            final int monthlyIncome, final int contractLength,
                            final int infrastructureCost, final int productionCost) {
        //to extend in part 2 of the homework when there will be 2 types of consumers;
        if (commonType.equals("consumer")) {
            return new Consumer(id, buget, monthlyIncome);
        } else if (commonType.equals("distributor")) {
            return new Distributor(id, contractLength, buget, infrastructureCost, productionCost);
        }
        return null;
    }
}
