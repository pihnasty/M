package hct.handlers;

import designpatterns.ObservableDS;
import fio.FileUI;
import hct.callbacks.OpenProjectCallBack;
import hct.callbacks.UploadNameCategoryCallBack;
import hct.tasks.OpenProjectTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;
import settings.Settings;

public class OpenProjectHandler {

    public void openProject(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        String fullFile = FileUI.getPathToFile(
            projectManager.getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("open.project")
        );
        String pathToProject =  FileUI.getPathToDirictory(fullFile).replace("\\","//");

        projectManager.getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH, pathToProject);

        OpenProjectTask openProjectTask = new OpenProjectTask(projectManager, pathToProject);
        OpenProjectCallBack openProjectCallBack = new OpenProjectCallBack();

        openProjectTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED
                    || newState == Worker.State.FAILED) {
                    openProjectCallBack.call(newState, openProjectTask.getException());
                }
            }
        );
        new Thread(openProjectTask).start();
    }


}
