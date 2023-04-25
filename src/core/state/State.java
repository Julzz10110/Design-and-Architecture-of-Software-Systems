package core.state;


public interface State {
    String getName();

    void updateDataFrame(StateContext context);
}