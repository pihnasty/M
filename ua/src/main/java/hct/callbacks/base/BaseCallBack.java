package hct.callbacks.base;

import dialogs.AlertDialog;
import hct.callbacks.StateTaskCallBack;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class BaseCallBack implements StateTaskCallBack<Worker.State,Throwable,Void>  {

    private String title;
    private String messageFailedText;


    public BaseCallBack(String title, String messageFailedText) {
        this.title = title;
        this.messageFailedText = messageFailedText;
    }

    public Void call(Worker.State state, Throwable throwable ){
//        AlertDialog.getAlertShow(MainWindowView.getResourceString(title)
//            ,MainWindowView.getResourceString(messageFailedText) + "\n " + throwable.getMessage());
        if(state==Worker.State.SUCCEEDED) {
            AlertDialog.getAlertShow(MainWindowView.getResourceString(title)
                ,MainWindowView.getResourceString(title));
        }
        if(state==Worker.State.FAILED){
            AlertDialog.getAlertShow(MainWindowView.getResourceString(messageFailedText)
                ,throwable.getMessage());
        }
        return null;
    }
}
