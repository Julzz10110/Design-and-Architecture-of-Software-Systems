package data.strategy;

import data.DataFrame;

public class ConcreteStrategyVar implements Strategy {

    @Override
    public Double execute(DataFrame df, String key, Object... others) {
        //System.out.println("Вызван метод execute() из ConcreteStrategyVar...");
        if (df.getData().get(key).size() == 0) return Double.NaN;
        try {
            double avg = (new data.strategy.ConcreteStrategyMean()).execute(df, key);
            double sum = 0.0;
            for (int i = 0; i < df.getData().get(key).size(); i++) {
                sum += ((double) df.getData().get(key).get(i) - avg) * ((double) df.getData().get(key).get(i) - avg);
            }
            return sum / (df.getData().get(key).size() - 1);
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }
}
