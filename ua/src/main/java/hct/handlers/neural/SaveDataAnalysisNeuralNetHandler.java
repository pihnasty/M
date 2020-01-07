package hct.handlers.neural;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.neural.SaveDataAnalysisNeuralNetCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.neural.SaveDataAnalysisNeuralNetTask;
import main.ProjectManager;

public class SaveDataAnalysisNeuralNetHandler extends BaseHandler {

    public void saveDataAnalysis(ObservableDS observableDS) {

        BaseTask task = new SaveDataAnalysisNeuralNetTask((ProjectManager) observableDS);

        BaseCallBack callBack = new SaveDataAnalysisNeuralNetCallBack(
        "save.data.analysis.neural.net.title"
        ,"save.data.analysis.neural.net.failed");

         addCallBack(task, callBack);
        new Thread(task).start();
    }

}
