package data.strategy;

import data.DataFrame;

public class ConcreteStrategySum implements Strategy {

    @Override
    public Double execute(DataFrame df, String key, Object... others) {
        System.out.println("Вызван метод execute() из ConcreteStrategySum...");
        if (df.getData().get(key).size() == 0) return Double.NaN;
        double sum = 0;
        for (Object obj : df.getData().get(key)) {
            try {
                if (obj != null && !Double.isNaN((Double) obj)) {
                    sum += (double) obj;
                }
            } catch (ClassCastException ex) {
                return Double.NaN;
            }
        }
        return sum;
    }
}