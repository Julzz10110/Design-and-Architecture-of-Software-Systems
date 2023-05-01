package charts;

import data.DataFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;


public class XYChart extends Chart {

    private XYSeriesCollection xyChartData;
    private JFreeChart xyChart;
    private XYPlot xyPlot;
    private String xLabel = "";
    private String yLabel = "";

    public XYChart(String title, boolean hasLegend) {
        super(title, hasLegend);
        xyChartData = new XYSeriesCollection();
        xyChart = ChartFactory.createXYLineChart(title, xLabel, yLabel, xyChartData, PlotOrientation.VERTICAL, hasLegend, true, false);
        xyPlot = xyChart.getXYPlot();
        xyPlot.setRangeGridlinePaint(gridColor);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        xyPlot.setRenderer(renderer);
    }


    public void createSeries(String seriesName) {
        xyChartData.addSeries(new XYSeries(seriesName));
    }

    public void addValue(String seriesName, Double x, Double y) {
        xyChartData.getSeries(seriesName).add(x, y);
    }

    @Override
    public ChartPanel getChartPanel() {
        chartPanel = new ChartPanel(xyChart);
        chartPanel.setSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        chartPanel.setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        chartPanel.getPopupMenu().remove(3);
        return chartPanel;
    }

    @Override
    public void build(DataFrame data, String xLabel, String yLabel) {
        xyPlot.getDomainAxis().setLabel(xLabel);
        xyPlot.getRangeAxis().setLabel(yLabel);
        createSeries(yLabel);
        for (int i = 0; i < data.getData().get(xLabel).size(); i++) {
            addValue(yLabel, (Double) data.getData().get(xLabel).get(i), (Double) data.getData().get(yLabel).get(i));

        }
    }
}