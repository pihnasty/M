package hct.handlers;

import designpatterns.ObservableDS;
import fio.FileUI;
import hct.callbacks.CreateTemplateExperimentPlanCallBack;
import hct.callbacks.UploadNameCategoryCallBack;
import hct.tasks.CreateTemplateExperimentPlanTask;
import hct.tasks.UploadNameCategoryTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;

public class CreateTemplateExperimentPlanHandler {

    public void createTemplateExperimentPlan(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        String pathToFile = FileUI.getSavePath(
            projectManager.getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.db.save")
        );

        CreateTemplateExperimentPlanTask createTemplateExperimentPlanTask = new  CreateTemplateExperimentPlanTask(projectManager, pathToFile);
        CreateTemplateExperimentPlanCallBack сreateTemplateExperimentPlanCallBack = new CreateTemplateExperimentPlanCallBack();

        createTemplateExperimentPlanTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED || newState == Worker.State.FAILED ) {
                    сreateTemplateExperimentPlanCallBack.call(newState,  createTemplateExperimentPlanTask.getException());
                }
            }
        );
        new Thread( createTemplateExperimentPlanTask).start();
    }

}
