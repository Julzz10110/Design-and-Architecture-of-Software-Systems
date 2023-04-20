package data.strategy;

import data.DataFrame;

public class StatCalculationContext {
    // Класс контекста использующий интерфейс стратегии
    private Strategy strategy;

    // Constructor
    public StatCalculationContext() {
    }

    // Set new strategy
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Number executeStrategy(DataFrame df, String key, Object... others) {
        return strategy.execute(df, key, others);
    }
}
