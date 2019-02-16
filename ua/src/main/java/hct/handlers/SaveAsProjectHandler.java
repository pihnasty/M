package hct.handlers;

import designpatterns.ObservableDS;
import fio.FileUI;
import hct.callbacks.SaveAsProjectCallBack;
import hct.callbacks.SaveProjectCallBack;
import hct.tasks.SaveAsProjectTask;
import hct.tasks.SaveProjectTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;
import settings.ProviderSettings;
import settings.Settings;

import java.io.File;

public class SaveAsProjectHandler {

    public void saveAsProject(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);


        String pathToProject = FileUI.getSavePath(
            projectManager.getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.db.save")
        );

        SaveAsProjectTask saveAsProjectTask =new SaveAsProjectTask(projectManager, pathToProject);
        SaveAsProjectCallBack saveAsProjectCallBack = new SaveAsProjectCallBack();


        saveAsProjectTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState==Worker.State.SUCCEEDED) {
                    saveAsProjectCallBack.call(newState,saveAsProjectTask.getException());
                }
            }
        );
        new Thread(saveAsProjectTask).start();
    }



}
