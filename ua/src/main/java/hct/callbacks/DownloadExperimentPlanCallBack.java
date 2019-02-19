package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class DownloadExperimentPlanCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("upload.name.category.title")
                ,MainWindowView.getResourceString("upload.name.category.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("upload.name.category.title")
                ,throwable.getMessage());
        }
        return null;
    }

}
