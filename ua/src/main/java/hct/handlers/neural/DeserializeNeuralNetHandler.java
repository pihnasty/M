package hct.handlers.neural;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.neural.DeserializeNeuralNetCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.neural.DeserializeNeuralNetTask;
import main.ProjectManager;

public class DeserializeNeuralNetHandler extends BaseHandler {

    public void deserialize(ObservableDS observableDS) {

        BaseTask task = new DeserializeNeuralNetTask((ProjectManager) observableDS);

        BaseCallBack callBack;
        callBack = new DeserializeNeuralNetCallBack(
        "deserialization.neural.net.title"
        ,"deserialization.neural.net.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

}
