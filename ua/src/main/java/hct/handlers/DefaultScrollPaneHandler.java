package hct.handlers;

import designpatterns.ObservableDS;
import hct.callbacks.CreateChartScrollPaneCallBack;
import hct.callbacks.DefaultScrollPaneCallBack;
import hct.tasks.CreateChartScrollPaneTask;
import hct.tasks.DefaultScrollPaneTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

public class DefaultScrollPaneHandler {

    public void clean(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        DefaultScrollPaneTask defaultScrollPaneTask = new DefaultScrollPaneTask(projectManager);
        DefaultScrollPaneCallBack defaultScrollPaneCallBack = new DefaultScrollPaneCallBack();

        defaultScrollPaneTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.FAILED) {
                    defaultScrollPaneCallBack.call(newState, defaultScrollPaneTask.getException());
                }
            }
        );
        new Thread(defaultScrollPaneTask).start();
    }
}
