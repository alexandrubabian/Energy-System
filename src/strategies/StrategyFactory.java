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

    public Strategy getStrategy(final EnergyChoiceStrategyType energyChoiceStrategyType) {
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
