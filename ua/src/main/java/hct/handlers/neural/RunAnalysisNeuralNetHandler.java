package hct.handlers.neural;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.neural.RunAnalysisNeuralNetCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.neural.RunAnalysisNeuralNetTask;
import main.ProjectManager;

public class RunAnalysisNeuralNetHandler extends BaseHandler {

    public void runAnalysis(ObservableDS observableDS) {

        BaseTask task = new RunAnalysisNeuralNetTask((ProjectManager) observableDS);

        BaseCallBack callBack = new RunAnalysisNeuralNetCallBack(
        "run.analysis.neural.net.title"
        ,"run.analysis.neural.net.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
