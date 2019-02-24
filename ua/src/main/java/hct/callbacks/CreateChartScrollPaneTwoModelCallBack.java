package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class CreateChartScrollPaneTwoModelCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        AlertDialog.getAlertShow(MainWindowView.getResourceString("created.chart.scroll.pane.title")
            ,MainWindowView.getResourceString("created.chart.scroll.pane.message.failed") + "\n " + throwable.getMessage());
        return null;
    }

}
