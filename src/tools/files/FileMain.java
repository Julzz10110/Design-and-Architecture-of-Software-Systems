package tools.files;

import java.io.File;
import java.util.HashMap;

import static tools.ChartSnapshotManager.DEFAULT_SNAPSHOTS_PATH;

public class FileMain {

    static void checkPermissions(File file) {
        System.out.println("Executable: "
                + file.canExecute());
        System.out.println("Readable: "
                + file.canRead());
        System.out.println("Writable: "
                + file.canWrite());
        System.out.println();
    }


    public static void main(String[] args) {
        FileAbstractFactory fileFactory;
        HashMap<String, String> fileNames = new HashMap<>();
        fileNames.put("table1.xlsx", "all");
        fileNames.put("table2.xls", "rd");
        fileNames.put("table3.csv", "rd");
        fileNames.put("image1.jpeg", "all");
        fileNames.put("image2.jpg", "rd");
        fileNames.put("image3.png", "rd");
        fileNames.put("image4.bmp", "all");
        fileNames.put("image5.bmp", "rd");
        fileNames.put("image6.tiff", "all");

        for (String fileName : fileNames.keySet()) {
            AbstractFile createdAbstractFile = null;
            File createdFile;
            int dotIndex = fileName.lastIndexOf('.');
            String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);

            if (extension.equals("xlsx") || extension.equals("xls") || extension.equals("csv")) {
                fileFactory = new TableFileFactory();
                if (fileNames.get(fileName).equals("all")) {
                    createdAbstractFile = fileFactory.createFullPermissionFile(fileName);
                } else if (fileNames.get(fileName).equals("rd")) {
                    createdAbstractFile = fileFactory.createReadOnlyFile(fileName);
                }
                createdFile = createdAbstractFile.createWithPermissions(DEFAULT_SNAPSHOTS_PATH + '/' + fileName);
                checkPermissions(createdFile);

            } else if (extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")
                    || extension.equals("bmp") || extension.equals("gif") || extension.equals("tiff")) {
                fileFactory = new ImageFileFactory();
                if (fileNames.get(fileName).equals("all")) {
                    createdAbstractFile = fileFactory.createFullPermissionFile(fileName);
                } else if (fileNames.get(fileName).equals("rd")) {
                    createdAbstractFile = fileFactory.createReadOnlyFile(fileName);
                }
                createdFile = createdAbstractFile.createWithPermissions(DEFAULT_SNAPSHOTS_PATH + '/' + fileName);
                checkPermissions(createdFile);
            } else {
                System.out.println(FileMain.class.getName() + " : Файл " + fileName + " не был создан.");
            }

        }
    }
}
