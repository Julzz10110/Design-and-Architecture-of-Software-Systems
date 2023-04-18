package charts;

import data.DataFrame;

import static charts.Chart.ChartType.*;


abstract class Creator {
    abstract public Chart factoryMethod(String title);
}

class XYChartCreator extends Creator {
    @Override
    public Chart factoryMethod(String title) {
        return new XYChart(title);
    }
}

class BarChartCreator extends Creator {
    @Override
    public Chart factoryMethod(String title) {
        return new BarChart(title);
    }
}

class PieChartCreator extends Creator {
    @Override
    public Chart factoryMethod(String title) {
        return new PieChart(title);
    }
}

class PieChart3DCreator extends Creator {
    @Override
    public Chart factoryMethod(String title) {
        return new PieChart3D(title);
    }
}

public class ChartFactory {
    public static Chart createChart(int chartType, String title, DataFrame data, String xLabel, String yLabel) {
        assert data != null;
        Creator chartCreator = switch (chartType) {
            case BAR_CHART -> new BarChartCreator();
            case XY_CHART -> new XYChartCreator();
            case PIE_CHART -> new PieChartCreator();
            case PIE_CHART_3D -> new PieChart3DCreator();
            default -> null;
        };
        assert chartCreator != null;
        Chart createdChart = chartCreator.factoryMethod(title);
        createdChart.build(data, xLabel, yLabel);

        System.out.println(ChartFactory.class.getName() + " : Построен график типа " + createdChart.getClass().getName() + " c помощью класса-создателя " + chartCreator.getClass().getName() + '.');

        return createdChart;
    }
}
