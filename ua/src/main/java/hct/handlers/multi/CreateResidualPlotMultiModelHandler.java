package hct.handlers.multi;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.multi.CalculateCoefficientsMultiParameterModelCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.multi.CreateResidualPlotMultiModelTask;
import main.ProjectManager;

public class CreateResidualPlotMultiModelHandler extends BaseHandler {

    public void buildResidualPlot(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        BaseTask task  = new CreateResidualPlotMultiModelTask(projectManager,true);
        BaseCallBack callBack
            = new CalculateCoefficientsMultiParameterModelCallBack(
            "multi.model.build.residual.plot.title"
            ,"multi.model.build.residual.plot.message.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
