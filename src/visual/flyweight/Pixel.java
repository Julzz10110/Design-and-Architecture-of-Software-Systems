package visual.flyweight;

import java.awt.*;

public class Pixel {
    private int x;
    private int y;
    private PixelType type;

    public Pixel(int x, int y, PixelType type) {
        super();
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public PixelType getType() {
        return type;
    }

    public void draw(Graphics g) {
        type.draw(g, x, y);
    }
}