package data.strategy;

import data.DataFrame;

public class ConcreteStrategyMean implements Strategy {

    @Override
    public Double execute(DataFrame df, String key, Object... others) {
        //System.out.println("Вызван метод execute() из ConcreteStrategyMean...");
        if (df.getData().get(key).size() == 0) return Double.NaN;
        try {
            return (new ConcreteStrategySum()).execute(df, key) / (new data.strategy.ConcreteStrategyCount()).execute(df, key);
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }
}
