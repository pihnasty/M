package hct.handlers.neural;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.neural.LearningNeuralNetCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.neural.LearningNeuralNetTask;
import main.ProjectManager;

public class LearningNeuralNetHandler extends BaseHandler {

    public void learning(ObservableDS observableDS) {

        BaseTask task = new LearningNeuralNetTask((ProjectManager) observableDS);

        BaseCallBack callBack
            = new LearningNeuralNetCallBack(
            "learning.neural.net.title"
            ,"learning.neural.net.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
