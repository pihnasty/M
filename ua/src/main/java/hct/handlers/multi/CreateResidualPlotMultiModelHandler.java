package hct.handlers.multi;

import designpatterns.ObservableDS;
import dialogs.AlertDialog;
import hct.callbacks.multi.CreateResidualPlotMultiModelCallBack;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.multi.CalculateCoefficientsMultiParameterModelCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.multi.CreateResidualPlotMultiModelTask;
import hct.tasks.base.BaseTask;
import hct.tasks.multi.CalculateCoefficientsMultiParameterModelTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;

public class CreateResidualPlotMultiModelHandler extends BaseHandler {

    public void buildResidualPlot(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        BaseTask task  = new CreateResidualPlotMultiModelTask(projectManager,false);
        BaseCallBack callBack
            = new CalculateCoefficientsMultiParameterModelCallBack(
            "multi.model.build.residual.plot.title"
            ,"multi.model.build.residual.plot.message.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
