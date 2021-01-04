package strategies;


public final class StrategyFactory {
    private static StrategyFactory instance;

    private StrategyFactory() {
    }

    public static StrategyFactory getInstance() {
        if (instance == null) {
            instance = new StrategyFactory();
        }
        return instance;
    }

    public Strategy getStrategy(EnergyChoiceStrategyType energyChoiceStrategyType) {
        //to extend in part 2 of the homework when there will be 2 types of consumers;
        if (energyChoiceStrategyType.equals(EnergyChoiceStrategyType.GREEN)) {
            return new GreenStrategy();
        } else if (energyChoiceStrategyType.equals(EnergyChoiceStrategyType.PRICE)) {
            return new PriceStrategy();
        } else if(energyChoiceStrategyType.equals(EnergyChoiceStrategyType.QUANTITY)) {
            return new QuantityStrategy();
        }
        return null;
    }

}
