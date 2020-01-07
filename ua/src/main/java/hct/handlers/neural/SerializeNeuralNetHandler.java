package hct.handlers.neural;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.neural.SerializeNeuralNetCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.neural.SerializeNeuralNetTask;
import main.ProjectManager;

public class SerializeNeuralNetHandler extends BaseHandler {

    public void serialize(ObservableDS observableDS) {

        BaseTask task = new SerializeNeuralNetTask((ProjectManager) observableDS);

        BaseCallBack callBack
            = new SerializeNeuralNetCallBack(
            "serialization.neural.net.title"
            ,"serialization.neural.net.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
