package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class SaveProjectCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("save.project.title")
                ,MainWindowView.getResourceString("save.project.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("save.project.title")
                ,throwable.getMessage());
        }
        return null;
    }

}
