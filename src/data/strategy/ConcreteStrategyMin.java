package data.strategy;

import data.DataFrame;

public class ConcreteStrategyMin implements Strategy {

    @Override
    public Double execute(DataFrame df, String key, Object... others) {
        //System.out.println("Вызван метод execute() из ConcreteStrategyMin...");
        if (df.getData().get(key).size() == 0) return Double.NaN;
        double min = Double.MAX_VALUE;
        try {
            for (Object obj : df.getData().get(key)) {
                if (obj != null && !Double.isNaN((Double) obj)) {
                    if ((double) obj < min) min = (double) obj;
                }
            }
            if (min != Double.MAX_VALUE) return min;
            else return Double.NaN;

        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }
}
