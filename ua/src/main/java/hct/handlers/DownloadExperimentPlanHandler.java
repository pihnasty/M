package hct.handlers;

import designpatterns.ObservableDS;
import fio.FileUI;
import hct.callbacks.DownloadExperimentPlanCallBack;
import hct.callbacks.UploadNameCategoryCallBack;
import hct.tasks.DownloadExperimentPlanTask;
import hct.tasks.UploadNameCategoryTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;

public class DownloadExperimentPlanHandler {

    public void downloadExperimentPlan(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        String pathToFile = FileUI.getPathToFile(
            projectManager.getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.file.category.name.download")
        );

        DownloadExperimentPlanTask downloadExperimentPlanTask = new DownloadExperimentPlanTask(projectManager, pathToFile);
        DownloadExperimentPlanCallBack downloadExperimentPlanCallBack = new DownloadExperimentPlanCallBack();

        downloadExperimentPlanTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED
                    || newState == Worker.State.FAILED) {
                    downloadExperimentPlanCallBack.call(newState, downloadExperimentPlanTask.getException());
                }
            }
        );
        new Thread(downloadExperimentPlanTask).start();
    }


}
