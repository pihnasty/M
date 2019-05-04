package hct.handlers.base;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.multi.CalculateCoefficientsMultiParameterModelCallBack;
import hct.tasks.base.BaseTask;
import hct.tasks.multi.CalculateCoefficientsMultiParameterModelTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

abstract public class BaseHandler {

    protected void addCallBack(BaseTask task, BaseCallBack baseCallBack) {

        task.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.FAILED) {
                    baseCallBack.call(newState, task.getException());
                }
            }
        );
    }

}
