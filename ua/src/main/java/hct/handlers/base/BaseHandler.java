package hct.handlers.base;

import hct.callbacks.base.BaseCallBack;
import hct.tasks.base.BaseTask;

abstract public class BaseHandler {

    protected void addCallBack(BaseTask task, BaseCallBack baseCallBack) {

        task.stateProperty().addListener(
            (observable, oldState, newState) -> {
                baseCallBack.call(newState,task.getException());
            }
        );
    }

}
