package hct.handlers;

import designpatterns.ObservableDS;
import dialogs.AlertDialog;
import fio.FileUI;
import hct.callbacks.CreateProjectCallBack;
import hct.callbacks.SaveProjectCallBack;
import hct.tasks.CreateProjectTask;
import hct.tasks.SaveProjectTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;
import settings.ProviderSettings;
import settings.Settings;

import java.io.File;

public class SaveProjectHandler {

    public void saveProject(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);
        SaveProjectTask saveProjectTask =new SaveProjectTask(projectManager);
        SaveProjectCallBack saveProjectCallBack = new SaveProjectCallBack();

        saveProjectTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState==Worker.State.SUCCEEDED) {
                    saveProjectCallBack.call(newState,saveProjectTask.getException());
                }
            }
        );
        new Thread(saveProjectTask).start();
    }



}
