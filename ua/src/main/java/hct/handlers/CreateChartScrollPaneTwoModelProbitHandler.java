package hct.handlers;

import designpatterns.ObservableDS;
import hct.callbacks.CreateChartScrollPaneTwoModelCallBack;
import hct.callbacks.CreateChartScrollPaneTwoModelProbitCallBack;
import hct.tasks.CreateChartScrollPaneTwoModelProbitTask;
import hct.tasks.CreateChartScrollPaneTwoModelTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

public class CreateChartScrollPaneTwoModelProbitHandler {

    public void buildGraph(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateChartScrollPaneTwoModelProbitTask createChartScrollPaneTwoModelProbitTask = new CreateChartScrollPaneTwoModelProbitTask(projectManager,false);
        addCallBack(createChartScrollPaneTwoModelProbitTask);
        new Thread(createChartScrollPaneTwoModelProbitTask).start();
    }



    public void  saveToPDF(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateChartScrollPaneTwoModelProbitTask createChartScrollPaneTwoModelProbitTask = new CreateChartScrollPaneTwoModelProbitTask(projectManager,true);
        addCallBack(createChartScrollPaneTwoModelProbitTask);
        new Thread(createChartScrollPaneTwoModelProbitTask).start();
    }

    private void addCallBack(CreateChartScrollPaneTwoModelProbitTask createChartScrollPaneTwoModelProbitTask) {
        CreateChartScrollPaneTwoModelProbitCallBack createChartScrollPaneTwoModelProbitCallBack = new CreateChartScrollPaneTwoModelProbitCallBack();

        createChartScrollPaneTwoModelProbitTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.FAILED) {
                    createChartScrollPaneTwoModelProbitCallBack.call(newState, createChartScrollPaneTwoModelProbitTask.getException());
                }
            }
        );
    }


}
