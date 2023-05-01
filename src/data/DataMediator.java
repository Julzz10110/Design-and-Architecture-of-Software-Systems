package data;

import charts.Chart;
import charts.ChartFactory;
import core.MainFrame;
import core.state.OriginalState;
import core.state.StateContext;
import tools.FileManager;

import javax.swing.table.DefaultTableModel;
import java.util.*;

import static core.MainFrame.getCanvasPanel;

public class DataMediator {

    private MainFrame mainFrame;
    private FileManager fileManager;
    private DataFrame dataFrame;
    private DefaultTableModel tableModel;

    private Queue<ArrayList<Object>> inputTableChanges = new LinkedList<>();
    private Stack<ArrayList<Object>> reversedInputTableChanges = new Stack<>();

    private DataMediator() {
    }

    public static DataMediator getInstance() {
        return DataMediatorHolder.instance;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void formDataFrame(ArrayList<Object> columnHeaders, ArrayList<ArrayList<Object>> tableData) {
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

    public Queue<ArrayList<Object>> getInputTableChanges() {
        return inputTableChanges;
    }

    public Stack<ArrayList<Object>> getReversedInputTableChanges() {
        return reversedInputTableChanges;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public DataFrame sendDataFrame() {
        return dataFrame;
    }

    public DefaultTableModel sendTabelModel() {
        return tableModel;
    }

    public void setOriginalDataFrame(StateContext context) {
        if (context.getState().getName().equals("original")) {
            OriginalState state = (OriginalState) context.getState();
            dataFrame = state.getOriginalDataFrame();
        } else System.err.println("ОШИБКА: Не удалось установить начальный DataFrame.");
    }

    public void setOriginalTabelModel(StateContext context) {
        if (context.getState().getName().equals("original")) {
            OriginalState state = (OriginalState) context.getState();
            tableModel = (DefaultTableModel) state.getOriginalTabelModel();
        } else System.err.println("ОШИБКА: Не удалось установить начальный TabelModel.");
    }

    public void commitChanges() {
        ArrayList<Object> lastChange;
        HashMap<String, Integer> occurencyMap = new HashMap<>();
        boolean start = true;
        while (!inputTableChanges.isEmpty()) {
            lastChange = inputTableChanges.poll();
            String key = lastChange.get(0).toString();
            int column = (int) lastChange.get(1);
            int row = (int) lastChange.get(2);
            Object newValue = lastChange.get(4);
            dataFrame.setValue(key, row, newValue);
            if (!start) {
                for (Map.Entry<String, Integer> occurencyPair : occurencyMap.entrySet()) {
                    if (!key.equals(occurencyPair.getKey())
                            || row != occurencyPair.getValue()) {
                        reversedInputTableChanges.push(lastChange);
                        occurencyMap.put(key, row);
                    }
                }
            } else reversedInputTableChanges.push(lastChange);

            tableModel.setValueAt(newValue, row, column);
        }
        reversedInputTableChanges.push(new ArrayList<>());
        //StateContext.getDataFrames().push(new DataFrame(dataFrame.getData()));
        dataFrame.printData();
        updateChart();
    }

    public void resetChanges() {
        ArrayList<Object> lastChange;
        reversedInputTableChanges.pop();
        while (!reversedInputTableChanges.isEmpty()
                && (lastChange = reversedInputTableChanges.pop()).size() > 0) {
            String key = lastChange.get(0).toString();
            int column = (int) lastChange.get(1);
            int row = (int) lastChange.get(2);
            Object oldValue = lastChange.get(3);
            dataFrame.setValue(key, row, oldValue);
            tableModel.setValueAt(oldValue, row, column);
        }
        //StateContext.getDataFrames().push(new DataFrame(dataFrame.getData()));
        dataFrame.printData();
        updateChart();
    }

    public void updateChart() {
        ArrayList<Object> chartConfiguration = ChartFactory.getChartConfiguration();
        if (chartConfiguration.size() > 0) {
            Chart chart = ChartFactory.createChart((Integer) chartConfiguration.get(0), (String) chartConfiguration.get(1),
                    dataFrame,
                    (String) chartConfiguration.get(3), (String) chartConfiguration.get(4), Chart.getLegendIncluded());
            MainFrame.setChart(chart);
            MainFrame.getCanvasPanel().removeAll();
            MainFrame.getCanvasPanel().add(chart.getChartPanel());
            MainFrame.getCanvasPanel().revalidate();
            getCanvasPanel().repaint();
        }
    }

    private static class DataMediatorHolder {
        private final static DataMediator instance = new DataMediator();
    }
}
