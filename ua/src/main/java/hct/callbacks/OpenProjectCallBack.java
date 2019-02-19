package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class OpenProjectCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("open.project.title")
                ,MainWindowView.getResourceString("open.project.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("open.project.title")
                ,throwable.getMessage());
        }
        return null;
    }

}
