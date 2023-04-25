package core.state;

public class StateExample {

    public static void main(String[] args) {
        StateContext context = new StateContext();
        //context.updateDataFrame();
        context.setState(new ChangedState());
        //context.updateDataFrame();
        context.setState(new ResetState());
        //context.updateDataFrame();
    }

}
