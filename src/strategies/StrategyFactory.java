package strategies;


public final class StrategyFactory {
    private static StrategyFactory instance;

    private StrategyFactory() {
    }

    /**
     * Singleton method to use the instance of the class
     */
    public static StrategyFactory getInstance() {
        if (instance == null) {
            instance = new StrategyFactory();
        }
        return instance;
    }

    /**
     * Method to return the strategy depending on the parameter used
     * @param energyChoiceStrategyType what kind of strategy it neeed to return
     */
    public Strategy getStrategy(final EnergyChoiceStrategyType energyChoiceStrategyType) {
        if (energyChoiceStrategyType.equals(EnergyChoiceStrategyType.GREEN)) {
            return new GreenStrategy();
        } else if (energyChoiceStrategyType.equals(EnergyChoiceStrategyType.PRICE)) {
            return new PriceStrategy();
        } else if (energyChoiceStrategyType.equals(EnergyChoiceStrategyType.QUANTITY)) {
            return new QuantityStrategy();
        }
        return null;
    }

}
