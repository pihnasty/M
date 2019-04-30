package hct.handlers.multi;

import designpatterns.ObservableDS;
import hct.callbacks.CalculateCoefficientsOneParameterModel_a_bCallBack;
import hct.callbacks.CreateResidualPlotMultiModelCallBack;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.multi.CalculateCoefficientsMultiParameterModelCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.CalculateCoefficientsOneParameterModel_a_bTask;
import hct.tasks.CreateResidualPlotMultiModelTask;
import hct.tasks.base.BaseTask;
import hct.tasks.multi.CalculateCoefficientsMultiParameterModelTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

public class CalculateCoefficientsMultiParameterModelHandler extends BaseHandler {

    public void calculate(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        BaseTask task = new CalculateCoefficientsMultiParameterModelTask(projectManager );

        BaseCallBack callBack
            = new CalculateCoefficientsMultiParameterModelCallBack(
            "multi.model.calculate.coefficients.title"
            ,"multi.model.calculate.coefficients.message.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
