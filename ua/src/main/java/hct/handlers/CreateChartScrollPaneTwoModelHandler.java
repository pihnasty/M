package hct.handlers;

import designpatterns.ObservableDS;
import hct.callbacks.CreateChartScrollPaneCallBack;
import hct.callbacks.CreateChartScrollPaneTwoModelCallBack;
import hct.tasks.CreateChartScrollPaneTask;
import hct.tasks.CreateChartScrollPaneTwoModelTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

public class CreateChartScrollPaneTwoModelHandler {

    public void buildGraph(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateChartScrollPaneTwoModelTask createChartScrollPaneTwoModelTask = new CreateChartScrollPaneTwoModelTask(projectManager,false);
        addCallBack(createChartScrollPaneTwoModelTask);
        new Thread(createChartScrollPaneTwoModelTask).start();
    }



    public void  saveToPDF(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateChartScrollPaneTwoModelTask createChartScrollPaneTwoModelTask = new CreateChartScrollPaneTwoModelTask(projectManager,true);
        addCallBack(createChartScrollPaneTwoModelTask);
        new Thread(createChartScrollPaneTwoModelTask).start();
    }

    private void addCallBack(CreateChartScrollPaneTwoModelTask createChartScrollPaneTwoModelTask) {
        CreateChartScrollPaneTwoModelCallBack createChartScrollPaneTwoModelCallBack = new CreateChartScrollPaneTwoModelCallBack();

        createChartScrollPaneTwoModelTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.FAILED) {
                    createChartScrollPaneTwoModelCallBack.call(newState, createChartScrollPaneTwoModelTask.getException());
                }
            }
        );
    }


}
