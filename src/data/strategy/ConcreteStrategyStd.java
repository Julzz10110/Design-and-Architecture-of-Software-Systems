package data.strategy;

import data.DataFrame;

public class ConcreteStrategyStd implements Strategy {

    @Override
    public Double execute(DataFrame df, String key, Object... others) {
        //System.out.println("Вызван метод execute() из ConcreteStrategyStd...");
        if (df.getData().get(key).size() == 0) return Double.NaN;
        try {
            return Math.sqrt((new ConcreteStrategyVar()).execute(df, key));
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }
}
