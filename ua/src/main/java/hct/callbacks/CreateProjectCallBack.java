package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class CreateProjectCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("Create.new.project.title")
                ,MainWindowView.getResourceString("Create.new.project.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("Create.new.project.title")
                ,throwable.getMessage());
        }
        return null;
    }

}
