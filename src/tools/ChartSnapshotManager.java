package tools;

import charts.Chart;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class ChartSnapshotManager {

    private String snapshotsPath = DEFAULT_SNAPSHOTS_PATH;

    public static final String DEFAULT_SNAPSHOTS_PATH = "/home/rustam/vizualizator_test_data/snapshots";

    public String getSnapshotsPath() {
        return snapshotsPath;
    }

    public void setSnapshotsPath(String snapshotsPath) {
        this.snapshotsPath = snapshotsPath;
    }

    public BufferedImage getScreenShot(Chart chart) {

        BufferedImage image = new BufferedImage(chart.getWidth(), chart.getHeight(), BufferedImage.TYPE_INT_RGB);
        // paints into image's Graphics
        chart.paint(image.getGraphics());
        return image;
    }

    public void getSaveSnapshot(Chart chart, String fileName) throws Exception {
        BufferedImage img = getScreenShot(chart);
        // write the captured image as a PNG
        ImageIO.write(img, "png", new File(fileName + ".png"));
    }

}
