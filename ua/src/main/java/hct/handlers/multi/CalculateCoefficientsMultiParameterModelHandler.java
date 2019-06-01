package hct.handlers.multi;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.multi.CalculateCoefficientsMultiParameterModelCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.multi.CalculateCoefficientsMultiParameterModelTask;
import main.ProjectManager;

public class CalculateCoefficientsMultiParameterModelHandler extends BaseHandler {

    public void calculate(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        BaseTask task = new CalculateCoefficientsMultiParameterModelTask(projectManager);

        BaseCallBack callBack
            = new CalculateCoefficientsMultiParameterModelCallBack(
            "multi.model.calculate.coefficients.title"
            ,"multi.model.calculate.coefficients.message.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
