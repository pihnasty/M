package hct.handlers;

import designpatterns.ObservableDS;
import hct.callbacks.CreateFactorsCallBack;
import hct.callbacks.SeparatedRawDataCallBack;
import hct.tasks.CreateFactorsTask;
import hct.tasks.SeparatedRawDataTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

public class CreateFactorsHandler {

    public void createFactors(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        CreateFactorsTask createFactorsTask = new CreateFactorsTask(projectManager);
        CreateFactorsCallBack createFactorsCallBack = new CreateFactorsCallBack();

        createFactorsTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED || newState == Worker.State.FAILED) {
                    createFactorsCallBack.call(newState, createFactorsTask.getException());
                }
            }
        );
        new Thread(createFactorsTask).start();
    }
}
