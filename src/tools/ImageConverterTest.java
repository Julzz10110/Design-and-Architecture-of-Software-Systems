package tools;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static tools.ChartSnapshotManager.DEFAULT_SNAPSHOTS_PATH;

class ImageConverterTest {

    @org.junit.jupiter.api.Test
    void testConvertNotExistingFile() {
        String fileName = "notexisting.png";
        String absoluteFileName = DEFAULT_SNAPSHOTS_PATH + '/' + fileName;
        String extension = "jpg";
        File convertedFile = ImageConverter.convert(absoluteFileName, extension);
        assertNull(convertedFile);
        System.out.println("Тест №1 успешно пройден");
    }

    @org.junit.jupiter.api.Test
    void testConvertNotDirectory() {
        String fileName = "2023-04-07T00:21:07.415326213.png";
        String absoluteFileName = DEFAULT_SNAPSHOTS_PATH + '/' + fileName;
        String extension = "jpg";
        File convertedFile = ImageConverter.convert(absoluteFileName, extension);
        assertEquals(false , convertedFile.isDirectory());
        System.out.println("Тест №2 успешно пройден");
    }

    @org.junit.jupiter.api.Test
    void testConvertedFileExtension() {
        String fileName = "2023-04-07T00:21:07.415326213.png";
        String absoluteFileName = DEFAULT_SNAPSHOTS_PATH + '/' + fileName;
        String extension = "tiff";
        File convertedFile = ImageConverter.convert(absoluteFileName, extension);
        assertNotNull(convertedFile);
        assertEquals(Optional.of(extension), Optional.ofNullable(convertedFile.getAbsolutePath().toString())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(absoluteFileName.lastIndexOf(".") + 1)));
        System.out.println("Тест №3 успешно пройден");
    }

    @org.junit.jupiter.api.Test
    void testForbiddenCharactersFilename() {
        String fileName = "forbi?dden_cha|rs.png";
        String absoluteFileName = DEFAULT_SNAPSHOTS_PATH + '/' + fileName;
        String extension = "jpg";
        File convertedFile = ImageConverter.convert(absoluteFileName, extension);
        assertNull(convertedFile);
        System.out.println("Тест №4 успешно пройден");
    }




}