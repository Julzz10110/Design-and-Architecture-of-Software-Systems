package data.strategy;

import data.DataFrame;

import java.util.ArrayList;
import java.util.Arrays;

public class ConcreteStrategyPercentile implements Strategy {

    @Override
    public Double execute(DataFrame df, String key, Object... others) {
        System.out.println("Вызван метод execute() из ConcreteStrategyPercentile...");
        int count;
        double percent = (Double) others[0];
        ArrayList<Object> data = df.getData().get(key);
        double[] normalizedData = new double[df.getData().get(key).size()];
        try {
            for (int i = 0; i < normalizedData.length; i++) normalizedData[i] = (double) data.get(i);
            Arrays.sort(normalizedData);
            int index = (int) (percent * normalizedData.length);

            return normalizedData[index];
        } catch (ClassCastException ex) {
            return Double.NaN;
        }
    }
}
