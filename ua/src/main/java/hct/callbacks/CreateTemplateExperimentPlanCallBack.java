package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class CreateTemplateExperimentPlanCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("сreate.template.experiment.title")
                ,MainWindowView.getResourceString("сreate.template.experiment.title.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("сreate.template.experiment.title")
                ,MainWindowView.getResourceString("сreate.template.experiment.title.message.failed")
                + "\n" +throwable.getMessage());
        }
        return null;
    }

}
