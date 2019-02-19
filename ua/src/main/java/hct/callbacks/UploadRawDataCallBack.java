package hct.callbacks;

import dialogs.AlertDialog;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class UploadRawDataCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    public Void call(Worker.State state, Throwable throwable ){
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("upload.raw.data.title")
                ,MainWindowView.getResourceString("upload.raw.data.message"));
        } else {
            AlertDialog.getAlertShow(MainWindowView.getResourceString("upload.raw.data.title")
                ,throwable.getMessage());
        }
        return null;
    }

}
