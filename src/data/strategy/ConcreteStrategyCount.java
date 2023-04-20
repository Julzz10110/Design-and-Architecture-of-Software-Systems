package data.strategy;

import data.DataFrame;

public class ConcreteStrategyCount implements Strategy {

    @Override
    public Integer execute(DataFrame df, String key, Object... others) {
        System.out.println("Вызван метод execute() из ConcreteStrategyCount...");
        int count = 0;
        for (Object obj : df.getData().get(key)) {
            if (obj != null) count++;
        }
        return count;
    }
}
