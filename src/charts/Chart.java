package charts;

import javax.swing.JPanel;
import java.awt.*;
import core.MainFrame;

public class Chart extends JPanel {
    protected static final int CHART_WIDTH = (int) (0.5 * MainFrame.MAIN_FRAME_WIDTH);
    protected static final int CHART_HEIGHT = (int) (0.8 * MainFrame.MAIN_FRAME_HEIGHT);
    protected Color gridColor = new Color(200, 200, 200, 200);
    protected String title;

    public Chart() {
        super();
        setPreferredSize(new Dimension(CHART_WIDTH, CHART_HEIGHT));
        setBackground(new Color(0xFFFFFF));
    }

}