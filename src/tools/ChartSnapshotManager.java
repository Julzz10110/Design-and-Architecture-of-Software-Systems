package tools;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class ChartSnapshotManager {

    private String snapshotsPath = DEFAULT_SNAPSHOTS_PATH;


    public static final String DEFAULT_SNAPSHOTS_PATH = System.getProperty("user.home");
    private boolean isEnabled = false;

    public String getSnapshotsPath() {
        return snapshotsPath;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setSnapshotsPath(String snapshotsPath) {
        this.snapshotsPath = snapshotsPath;
    }

    public void getSaveSnapshot(ChartPanel chartPanel, String fileName) throws Exception {
        try {
            OutputStream out = new FileOutputStream(fileName + ".png");
            ChartUtils.writeChartAsPNG(out, chartPanel.getChart(), chartPanel.getWidth(), chartPanel.getHeight());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
