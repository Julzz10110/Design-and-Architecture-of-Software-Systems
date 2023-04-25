package core.state;

import data.DataFrame;
import data.DataMediator;

import javax.swing.table.TableModel;

public class OriginalState implements State {
    private static final String NAME = "original";
    private static DataFrame originalDataFrame;
    private static TableModel originalTabelModel;

    public String getName() {
        return NAME;
    }

    public DataFrame getOriginalDataFrame() {
        return originalDataFrame;
    }

    public TableModel getOriginalTabelModel() {
        return originalTabelModel;
    }

    @Override
    public void updateDataFrame(StateContext context) {
        originalDataFrame = DataMediator.getInstance().sendDataFrame();
        originalTabelModel = DataMediator.getInstance().sendTabelModel();
    }
}
