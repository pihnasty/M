package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class SeparatedRawDataCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("separated.raw.data.title")
                ,MainWindowView.getResourceString("separated.raw.data.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("separated.raw.data.title")
                ,MainWindowView.getResourceString("separated.raw.data.message.failed") + "\n " + throwable.getMessage());
        }
        return null;
    }

}
