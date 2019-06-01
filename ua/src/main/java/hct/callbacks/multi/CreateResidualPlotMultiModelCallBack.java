package hct.callbacks.multi;

import dialogs.AlertDialog;
import hct.callbacks.StateTaskCallBack;
import hct.callbacks.base.BaseCallBack;
import javafx.concurrent.Worker;
import mainwindows.MainWindowView;

public  class CreateResidualPlotMultiModelCallBack extends BaseCallBack {

    public CreateResidualPlotMultiModelCallBack(String title, String messageFailedText) {
        super(title, messageFailedText);
    }
}
