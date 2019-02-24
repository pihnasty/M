package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class CalculateCoefficientsOneParameterModel_a_bCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("calculate.coefficient.a.b.one.param.model.title")
                ,MainWindowView.getResourceString("calculate.coefficient.a.b.one.param.model.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("calculate.coefficient.a.b.one.param.model.title")
                ,MainWindowView.getResourceString("calculate.coefficient.a.b.one.param.model.failed") + "\n " + throwable.getMessage());
        }
        return null;
    }

}
