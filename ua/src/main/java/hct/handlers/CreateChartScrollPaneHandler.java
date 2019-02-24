package hct.handlers;

import designpatterns.ObservableDS;
import fio.FileUI;
import hct.callbacks.CreateChartScrollPaneCallBack;
import hct.callbacks.UploadRawDataCallBack;
import hct.tasks.CreateChartScrollPaneTask;
import hct.tasks.UploadRawDataTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;

public class CreateChartScrollPaneHandler {

    public void buildGraph(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateChartScrollPaneTask createChartScrollPaneTask = new CreateChartScrollPaneTask(projectManager,false);
        addCallBack(createChartScrollPaneTask);
        new Thread(createChartScrollPaneTask).start();
    }



    public void  saveToPDF(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateChartScrollPaneTask createChartScrollPaneTask = new CreateChartScrollPaneTask(projectManager,true);
        addCallBack(createChartScrollPaneTask);
        new Thread(createChartScrollPaneTask).start();
    }

    private void addCallBack(CreateChartScrollPaneTask createChartScrollPaneTask) {
        CreateChartScrollPaneCallBack createChartScrollPaneCallBack = new CreateChartScrollPaneCallBack();

        createChartScrollPaneTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.FAILED) {
                    createChartScrollPaneCallBack.call(newState, createChartScrollPaneTask.getException());
                }
            }
        );
    }


}
