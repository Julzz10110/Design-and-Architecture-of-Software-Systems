package visual.flyweight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NoisePanel extends JPanel {
    private List<Pixel> pixels = new ArrayList<>();

    public static int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public void setPixel(int x, int y, String name, Color color) {
        PixelType type = PixelFactory.getPixelType(name, color);
        Pixel pixel = new Pixel(x, y, type);
        pixels.add(pixel);
    }

    @Override
    public void paint(Graphics graphics) {
        for (Pixel pixel : pixels) {
            pixel.draw(graphics);
        }
    }

    public List<Pixel> getPixels() {
        return pixels;
    }
}