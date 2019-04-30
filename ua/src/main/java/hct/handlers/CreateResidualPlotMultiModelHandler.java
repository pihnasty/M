package hct.handlers;

import designpatterns.ObservableDS;
import hct.callbacks.CreateChartScrollPaneTwoModelCallBack;
import hct.callbacks.CreateResidualPlotMultiModelCallBack;
import hct.tasks.CreateChartScrollPaneTwoModelTask;
import hct.tasks.CreateResidualPlotMultiModelTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

public class CreateResidualPlotMultiModelHandler {

    public void buildResidualPlot(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        CreateResidualPlotMultiModelTask createResidualPlotMultiModelTask
            = new CreateResidualPlotMultiModelTask(projectManager,false);
        addCallBack(createResidualPlotMultiModelTask);
        new Thread(createResidualPlotMultiModelTask).start();
    }

    private void addCallBack(CreateResidualPlotMultiModelTask createResidualPlotMultiModelTask) {
        CreateResidualPlotMultiModelCallBack createResidualPlotMultiModelCallBack = new CreateResidualPlotMultiModelCallBack();

        createResidualPlotMultiModelTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.FAILED) {
                    createResidualPlotMultiModelCallBack.call(newState, createResidualPlotMultiModelTask.getException());
                }
            }
        );
    }


}
