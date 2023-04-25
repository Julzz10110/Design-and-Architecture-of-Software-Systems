package visual.flyweight;

import java.awt.*;

public class PixelType {
    private String name;
    private Color color;

    public PixelType(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void draw(Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x - 5, y - 10, 5, 5);
    }

}
