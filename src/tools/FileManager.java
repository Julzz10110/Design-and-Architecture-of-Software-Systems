package tools;

import com.opencsv.CSVReader;
import data.DataFrame;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FileManager {
    private boolean columnsIterating;
    protected ArrayList<Object> columnHeaders;
    protected ArrayList<ArrayList<Object>> tableData;
    
    public Object[] loadDataCSV(String pathName) {
        Path filePath = Paths.get(pathName);
        ArrayList<String[]> inputData = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            CSVReader csvReader = new CSVReader(reader);
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    inputData.add(line);
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        columnHeaders = new ArrayList<>();
        tableData = new ArrayList<>();
        columnHeaders.addAll(Arrays.asList(inputData.get(0)));

        for (int i = 1; i < inputData.size(); i++) {
            ArrayList<Object> row = new ArrayList<>();
            for (int j = 0; j < inputData.get(i).length; j++) {
                try {
                    row.add(Double.parseDouble(inputData.get(i)[j].trim()));
                } catch (NumberFormatException ex) {
                    row.add(inputData.get(i)[j].trim());
                }
            }
            //row.addAll(Arrays.asList(inputData.get(i)));
            tableData.add(row);
        }
        DataFrame data = new DataFrame(columnHeaders, tableData);

        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders.toArray(), 0);
        for (ArrayList<Object> row : tableData) {
            tableModel.addRow(row.toArray(new Object[0]));
        }

        return new Object[]{data, tableModel};
    }
    public Object[] loadDataExcel(String pathName) {

        try
        {
            columnsIterating = true;
            columnHeaders = new ArrayList<>();
            tableData = new ArrayList<>();

            Object wb;
            Object sheet;
            Iterator<Row> itr;

            File file = new File(pathName);
            FileInputStream fis = new FileInputStream(file);

            int dotIndex = file.getAbsolutePath().lastIndexOf('.');
            String extension = (dotIndex == -1) ? "" : file.getAbsolutePath().substring(dotIndex + 1);
            if (extension.equals("xlsx")) {
                wb = new XSSFWorkbook(fis);
                sheet = ((XSSFWorkbook) wb).getSheetAt(0);
                itr = ((XSSFSheet) sheet).iterator();
            } else if (extension.equals("xls")) {
                wb = new HSSFWorkbook(fis);
                sheet = ((HSSFWorkbook) wb).getSheetAt(0);
                itr = ((HSSFSheet) sheet).iterator();
            } else {
                System.out.println("ОШИБКА: Невозможно прочитать файл");
                return null;
            }
            while (itr.hasNext())
            {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                ArrayList<Object> tableDataRow = new ArrayList<>();
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t\t\t");
                            if (columnsIterating) columnHeaders.add(cell.getStringCellValue().trim());
                            else tableDataRow.add(cell.getStringCellValue().trim());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t\t\t");
                            if (columnsIterating) columnHeaders.add(cell.getNumericCellValue());
                            else tableDataRow.add(cell.getNumericCellValue());
                            break;
                        default:
                    }
                }
                System.out.println("");
                tableData.add(tableDataRow);
                columnsIterating = false;
                ((Workbook) wb).close();
                fis.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(columnHeaders.toArray()[1] + " "+ tableData.toArray()[0]);

        DataFrame data = new DataFrame(columnHeaders, tableData);

        DefaultTableModel tableModel = new DefaultTableModel(columnHeaders.toArray(), 0);
        for (ArrayList<Object> row : tableData) {
            tableModel.addRow(row.toArray(new Object[0]));
        }
        return new Object[]{data, tableModel};
    }


}
