package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class CreateFactorsCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("created.factors.title")
                ,MainWindowView.getResourceString("created.factors.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("created.factors.title")
                ,MainWindowView.getResourceString("created.factors.message.failed") + "\n " + throwable.getMessage());
        }
        return null;
    }

}
