package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;

public class PieChart3D extends PieChart {
    public PieChart3D(String title) {
        super(title);
        pieChartData = new DefaultPieDataset();
        pieChart = ChartFactory.createPieChart3D(title, pieChartData, true, true, false);
    }

    @Override
    public ChartPanel getChartPanel() {
        chartPanel = new ChartPanel(((PiePlot3D) pieChart.getPlot()).getChart());
        chartPanel.setSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        chartPanel.setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        chartPanel.getPopupMenu().remove(3);
        return chartPanel;
    }
}
