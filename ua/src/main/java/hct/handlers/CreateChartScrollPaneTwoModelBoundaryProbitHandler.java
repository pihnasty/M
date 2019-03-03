package hct.handlers;

import designpatterns.ObservableDS;
import hct.callbacks.CreateChartScrollPaneTwoModelBoundaryProbitCallBack;
import hct.callbacks.CreateChartScrollPaneTwoModelCallBack;
import hct.tasks.CreateChartScrollPaneTwoModelBoundaryProbitTask;
import hct.tasks.CreateChartScrollPaneTwoModelTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

public class CreateChartScrollPaneTwoModelBoundaryProbitHandler {

    public void buildGraph(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateChartScrollPaneTwoModelBoundaryProbitTask createChartScrollPaneTwoModelBoundaryProbitTask = new CreateChartScrollPaneTwoModelBoundaryProbitTask(projectManager,false);
        addCallBack(createChartScrollPaneTwoModelBoundaryProbitTask);
        new Thread(createChartScrollPaneTwoModelBoundaryProbitTask).start();
    }



    public void  saveToPDF(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateChartScrollPaneTwoModelBoundaryProbitTask createChartScrollPaneTwoModelBoundaryProbitTask = new CreateChartScrollPaneTwoModelBoundaryProbitTask(projectManager,true);
        addCallBack(createChartScrollPaneTwoModelBoundaryProbitTask);
        new Thread(createChartScrollPaneTwoModelBoundaryProbitTask).start();
    }

    private void addCallBack(CreateChartScrollPaneTwoModelBoundaryProbitTask createChartScrollPaneTwoModelBoundaryProbitTask) {
        CreateChartScrollPaneTwoModelBoundaryProbitCallBack createChartScrollPaneTwoModelBoundaryProbitCallBack = new CreateChartScrollPaneTwoModelBoundaryProbitCallBack();

        createChartScrollPaneTwoModelBoundaryProbitTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.FAILED) {
                    createChartScrollPaneTwoModelBoundaryProbitCallBack.call(newState, createChartScrollPaneTwoModelBoundaryProbitTask.getException());
                }
            }
        );
    }


}
