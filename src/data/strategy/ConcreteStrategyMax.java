package data.strategy;

import data.DataFrame;

public class ConcreteStrategyMax implements Strategy {

    @Override
    public Double execute(DataFrame df, String key, Object... others) {
        System.out.println("Вызван метод execute() из ConcreteStrategyMax...");
        if (df.getData().get(key).size() == 0) return Double.NaN;
        double max = Double.MIN_VALUE;
        try {
            for (Object obj : df.getData().get(key)) {
                if (obj != null && !Double.isNaN((Double) obj)) {
                    if ((double) obj > max) max = (double) obj;
                }
            }
            if (max != Double.MIN_VALUE) return max;
            else return Double.NaN;
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }
}
