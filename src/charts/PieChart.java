package charts;

import data.DataFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;

public class PieChart extends Chart {
    protected DefaultPieDataset pieChartData;
    protected JFreeChart pieChart;

    public PieChart(String title, boolean hasLegend) {
        super(title, hasLegend);
        pieChartData = new DefaultPieDataset();
        pieChart = ChartFactory.createPieChart(title, pieChartData, hasLegend, true, false);

    }

    public void addValue(Double value, String itemName) {
        pieChartData.setValue(itemName, value.doubleValue());
    }

    @Override
    public ChartPanel getChartPanel() {
        chartPanel = new ChartPanel(pieChart);
        chartPanel.setSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        chartPanel.setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        chartPanel.getPopupMenu().remove(3);
        return chartPanel;
    }

    @Override
    public void build(DataFrame data, String xLabel, String yLabel) {
        for (int i = 0; i < data.getData().get(xLabel).size() - 1; i++) {
            addValue((Double) data.getData().get(yLabel).get(i), (String) data.getData().get(xLabel).get(i));
        }
    }
}
