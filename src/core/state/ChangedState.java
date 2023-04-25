package core.state;

import data.DataMediator;


public class ChangedState implements State {
    private static final String NAME = "changed";

    public String getName() {
        return NAME;
    }

    @Override
    public void updateDataFrame(StateContext context) {
        DataMediator.getInstance().commitChanges();
    }
}
