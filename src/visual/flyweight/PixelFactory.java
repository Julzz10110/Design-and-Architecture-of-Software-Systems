package visual.flyweight;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PixelFactory {

    static Map<String, PixelType> pixelTypes = new HashMap<>();

    public static PixelType getPixelType(String name, Color color) {
        PixelType result = pixelTypes.get(name);
        if (result == null) {
            result = new PixelType(name, color);
            pixelTypes.put(name, result);
        }
        return result;
    }
}
