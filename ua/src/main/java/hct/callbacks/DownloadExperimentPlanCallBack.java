package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class DownloadExperimentPlanCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("upload.experiment.plan.title")
                ,MainWindowView.getResourceString("upload.experiment.plan.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("upload.experiment.plan.title")
                ,throwable.getMessage());
        }
        return null;
    }

}
