package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DataFrame implements Cloneable {
    private ArrayList<Object> columnHeaders;
    private ArrayList<ArrayList<Object>> tableData;

    public DataFrame(ArrayList<Object> columnHeaders, ArrayList<ArrayList<Object>> tableData) {
        this.columnHeaders = columnHeaders;
        this.tableData = tableData;
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<ArrayList<Object>> normalizedData = new ArrayList<>();
        for (ArrayList<Object> row : tableData) {
            ArrayList<Object> normalizedRow = new ArrayList<>();
            for (Object cell : row) {
                try {
                    normalizedRow.add((Double) cell);
                } catch (ClassCastException ex) {
                    normalizedRow.add((String) cell);
                }
            }
            normalizedData.add(normalizedRow);
        }
        normalizedData = transpose(normalizedData);
        for (Object column : columnHeaders) columns.add(column.toString());
        for (int i = 0; i < columns.size(); i++) {
            data.put(columns.get(i), normalizedData.get(i));
            //System.out.println(columns.get(i) + ":    " + normalizedData.get(i).toString());
        }
        //System.out.println(normalizedData);
    }
    public DataFrame(HashMap<String, ArrayList<Object>> data) {
        this.data = data;
    }
    private HashMap<String, ArrayList<Object>> data = new HashMap<>();

    public ArrayList<Object> getColumnHeaders() {
        return columnHeaders;
    }

    public ArrayList<ArrayList<Object>> getTableData() {
        return tableData;
    }

    public HashMap<String, ArrayList<Object>> getData() {
        return data;
    }

    public void setData(HashMap<String, ArrayList<Object>> data) {
        this.data = data;
    }

    public ArrayList<ArrayList<Object>> transpose(ArrayList<ArrayList<Object>> matrixIn) {
        ArrayList<ArrayList<Object>> matrixOut = new ArrayList<>();
        int rowCount = matrixIn.size();
        int colCount = 0;


        for (int i = 0; i < rowCount; i++) {
            ArrayList<Object> row = matrixIn.get(i);
            int rowSize = row.size();
            if (rowSize > colCount) {
                colCount = rowSize;
            }
        }

        for (int r = 0; r < rowCount; r++){
            ArrayList<Object> innerIn = matrixIn.get(r);

            for (int c = 0; c < colCount; c++){

                //add it to the outgoing matrix
                //get matrixOut[c], or create it
                ArrayList<Object> matrixOutRow = new ArrayList<>();
                if (r != 0) {
                    try{
                        matrixOutRow = matrixOut.get(c);
                    }catch(java.lang.IndexOutOfBoundsException e){
                        System.out.println("Ошибка транспонирования!\n");
                    }
                }
                //add innerIn[c]
                try{
                    matrixOutRow.add(innerIn.get(c));
                }catch (java.lang.IndexOutOfBoundsException e){
                }

                try {
                    matrixOut.set(c,matrixOutRow);
                }catch(java.lang.IndexOutOfBoundsException e){
                    matrixOut.add(matrixOutRow);
                }
            }
        }
        return matrixOut;
    }

    public void sortDataByKey(String key) {
        Collections.sort((ArrayList) data.get(key));
    }

    public void sortAllData() {
        for (String key : data.keySet()) sortDataByKey(key);
    }

    public void setValue(String key, int pos, Object value) {
        try {
            data.get(key).set(pos, Double.valueOf(value.toString()));
        } catch (NumberFormatException ex) {
            data.get(key).set(pos, (String) value);
        }
        //System.out.println("TEST: " + data.get(key).get(pos).getClass());
    }

    public DataFrame clone() {
        DataFrame clone = new DataFrame(data);
        return clone;
    }

    public void printData() {
        for (String key : data.keySet()) {
            System.out.print(key + " : ");
            for (Object el : data.get(key)) {
                System.out.print(el.toString() + " ");
            }
            System.out.print('\n');
        }


    }

}
