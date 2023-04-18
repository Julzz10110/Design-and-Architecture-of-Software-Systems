package charts;

import core.MainFrame;
import data.DataFrame;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

public abstract class Chart extends JPanel {
    protected static final int CHART_WIDTH = (int) (0.5 * MainFrame.MAIN_FRAME_WIDTH);
    protected static final int CHART_HEIGHT = (int) (0.8 * MainFrame.MAIN_FRAME_HEIGHT);
    protected Color gridColor = new Color(0, 0, 0, 200);


    protected String title;
    protected ChartPanel chartPanel;


    public Chart(String title) {
        super();
        this.title = title;
        setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setBackground(new Color(0xFFFFFF));
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getWidth() {
        return CHART_WIDTH;
    }

    @Override
    public int getHeight() {
        return CHART_HEIGHT;
    }

    public abstract void build(DataFrame data, String xLabel, String yLabel);

    public abstract ChartPanel getChartPanel();

    public final class ChartType {
        public static final int XY_CHART = 0;
        public static final int BAR_CHART = 1;
        public static final int PIE_CHART = 2;
        public static final int PIE_CHART_3D = 3;

        private ChartType() {
        }
    }

}