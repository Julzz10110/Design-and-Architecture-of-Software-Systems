package core.state;

import charts.Chart;
import data.DataFrame;

import java.util.Stack;

public class StateContext {

    private static Stack<DataFrame> dataFrames = new Stack<>();
    private static Stack<Chart> charts = new Stack<>();
    private State state = new OriginalState();

    public static Stack<DataFrame> getDataFrames() {
        return dataFrames;
    }

    public static Stack<Chart> getCharts() {
        return charts;
    }

    public void updateDataFrame() {
        state.updateDataFrame(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        System.out.println("Изменение состояния на " + state.getName() + "...");
        this.state = state;
    }

}
