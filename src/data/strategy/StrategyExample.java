package data.strategy;

import data.DataFrame;

import java.util.ArrayList;
import java.util.Arrays;

public class StrategyExample {
    // Тестовое приложение

    public static void main(String[] args) {

        DataFrame dataFrame = new DataFrame(
                new ArrayList<>(Arrays.asList("Name", "Age", "Height", "Weight")),
                new ArrayList<>(Arrays.asList(
                        new ArrayList<>(Arrays.asList("Harry", 17., 167., 67.)),
                        new ArrayList<>(Arrays.asList("Mary", 34., 157., 54.)),
                        new ArrayList<>(Arrays.asList("John", 64., 189., 74.)),
                        new ArrayList<>(Arrays.asList("Jane", 41., 173., 65.)),
                        new ArrayList<>(Arrays.asList("Incognito", 25., 184., 86.))
                ))
        );

        StatCalculationContext context = new StatCalculationContext();

        context.setStrategy(new ConcreteStrategyMin());
        Number resultA = context.executeStrategy(dataFrame, "Age");

        context.setStrategy(new ConcreteStrategyMax());
        Number resultB = context.executeStrategy(dataFrame, "Height");

        context.setStrategy(new data.strategy.ConcreteStrategyMean());
        Number resultC = context.executeStrategy(dataFrame, "Weight");

        System.out.println("Результат A : " + resultA);
        System.out.println("Результат B : " + resultB);
        System.out.println("Результат C : " + resultC);

    }
}
