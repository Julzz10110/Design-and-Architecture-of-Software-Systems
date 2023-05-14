package tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;

public class ImageConverter {

    public static File convert(String inputFileName, String format) {
        int dotIndex = inputFileName.lastIndexOf('.');
        char[] forbiddenChars = new char[]{'/', '\\', '*', '?', '<', '>', '|'};
        String shortInputFileName = Paths.get(inputFileName).getFileName().toString();
        for (char ch : forbiddenChars) {
            if (shortInputFileName.contains(String.valueOf(ch)) || format.contains(String.valueOf(ch))) {
                System.err.println("ОШИБКА: Имя файла содержит запрещенные символы");
                return null;
            }
        }
        String outputFileName = (dotIndex == -1) ? inputFileName + '.' + format : inputFileName.substring(0, dotIndex) + '.' + format;
        if (outputFileName.equals(inputFileName)) {
            System.out.println("Конвертация невозможна: одинаковые расширения входного и выходного файлов");
            return null;
        }
        File inputFile = new File(inputFileName);
        if (inputFile.isDirectory()) {
            System.err.println("ОШИБКА: Выбрана директория, а не файл");
            return null;
        }
        File outputFile = new File(outputFileName);
        try (InputStream is = new FileInputStream(inputFile)) {
            BufferedImage image = ImageIO.read(is);
            try (OutputStream os = new FileOutputStream(outputFile)) {
                if (!format.equals("jpeg")) {
                    ImageIO.write(image, format, os);
                } else {
                    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
                    newImage.createGraphics().drawImage(image, 0, 0, Color.BLACK, null);
                    ImageIO.write(newImage, "jpeg", os);
                }
            } catch (Exception exp) {
                System.err.println("ОШИБКА: Нет такого выходящего файла или каталога");
                return null;
            }
        } catch (Exception exp) {
            System.err.println("ОШИБКА: Нет такого входящего файла или каталога");
            return null;
        }
        return outputFile;
    }
}
