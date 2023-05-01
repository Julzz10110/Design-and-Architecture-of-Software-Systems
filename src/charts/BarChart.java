package charts;


import data.DataFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class BarChart extends Chart {
    private DefaultCategoryDataset barChartData;
    private JFreeChart barChart;
    private CategoryPlot barCategoryPlot;
    private String xLabel = "";
    private String yLabel = "";

    public BarChart(String title, boolean hasLegend) {
        super(title, hasLegend);
        barChartData = new DefaultCategoryDataset();
        barChart = ChartFactory.createBarChart(title, xLabel, yLabel, barChartData, PlotOrientation.VERTICAL, hasLegend, true, false);
        barCategoryPlot = barChart.getCategoryPlot();
        barCategoryPlot.setRangeGridlinePaint(gridColor);

    }

    public void addValue(Double value, String itemName) {
        barChartData.setValue(value.doubleValue(), yLabel, itemName);
    }

    @Override
    public ChartPanel getChartPanel() {
        chartPanel = new ChartPanel(barChart);
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
