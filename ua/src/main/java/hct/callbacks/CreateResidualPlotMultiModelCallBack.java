package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class CreateResidualPlotMultiModelCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        AlertDialog.getAlertShow(MainWindowView.getResourceString("multi.model.build.residual.plot.title")
            ,MainWindowView.getResourceString("multi.model.build.residual.plot.message.failed") + "\n " + throwable.getMessage());
        return null;
    }
}
