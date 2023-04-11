package data;

import core.MainFrame;
import tools.FileManager;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DataMediator {

    private MainFrame mainFrame;
    private FileManager fileManager;
    private DataFrame dataFrame;
    private DefaultTableModel tableModel;

    private DataMediator() {
    }

    public static DataMediator getInstance() {
        return DataMediator.DataFrameMediatorHolder.instance;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void formDataframe(ArrayList<Object> columnHeaders, ArrayList<ArrayList<Object>> tableData) {
        dataFrame = new DataFrame(columnHeaders, tableData);
    }

    public void formTableModel(ArrayList<Object> columnHeaders, ArrayList<ArrayList<Object>> tableData) {
        tableModel = new DefaultTableModel(columnHeaders.toArray(), 0);
        for (ArrayList<Object> row : tableData) {
            tableModel.addRow(row.toArray(new Object[0]));
        }

    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public DataFrame sendDataframe() {
        return dataFrame;
    }

    public DefaultTableModel sendTabelModel() {
        return tableModel;
    }

    private static class DataFrameMediatorHolder {
        private final static DataMediator instance = new DataMediator();
    }


}
