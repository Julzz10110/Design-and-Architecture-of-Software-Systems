package core.state;

import core.MainFrame;
import data.DataMediator;

import java.util.EmptyStackException;

public class ResetState implements State {
    private static final String NAME = "reset";

    public String getName() {
        return NAME;
    }

    @Override
    public void updateDataFrame(StateContext context) {
        try {
            DataMediator.getInstance().resetChanges();
        } catch (EmptyStackException ex) {
            System.err.println("ОШИБКА: невозможно отменить изменение (стек изменений пуст).");
            context.setState(new OriginalState());
            DataMediator.getInstance().setOriginalDataFrame(context);
            DataMediator.getInstance().setOriginalTabelModel(context);
            MainFrame.getInputTable().setModel(DataMediator.getInstance().sendTabelModel());
            DataMediator.getInstance().sendDataFrame().printData();
        }
    }
}
