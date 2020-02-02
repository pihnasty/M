package hct.handlers.neural;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.neural.ContinueLearningNeuralNetCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.neural.ContinueLearningNeuralNetTask;
import main.ProjectManager;

public class ContinueLearningNeuralNetHandler extends BaseHandler {

    public void continueLearning(ObservableDS observableDS) {

        BaseTask task = new ContinueLearningNeuralNetTask((ProjectManager) observableDS);

        BaseCallBack callBack = new ContinueLearningNeuralNetCallBack(
            "continue.learning.neural.net.title"
            ,"learning.neural.net.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
