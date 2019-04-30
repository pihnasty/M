package hct.callbacks.multi;

import dialogs.AlertDialog;
import hct.callbacks.StateTaskCallBack;
import hct.callbacks.base.BaseCallBack;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class CalculateCoefficientsMultiParameterModelCallBack extends BaseCallBack {

    public CalculateCoefficientsMultiParameterModelCallBack(String title, String messageFailedText) {
        super(title, messageFailedText);
    }

}
