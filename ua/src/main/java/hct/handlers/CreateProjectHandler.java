package hct.handlers;

import designpatterns.ObservableDS;
import dialogs.AlertDialog;
import fio.FileUI;
import hct.callbacks.CreateProjectCallBack;
import hct.tasks.CreateProjectTask;
import javafx.concurrent.Worker;
import logging.LoggerP;
import main.ProjectManager;
import mainwindows.MainWindowView;
import settings.ProviderSettings;
import settings.Settings;

import java.io.File;

public class CreateProjectHandler {

    public void createProject(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        String pathToProject = FileUI.getSavePath(
            projectManager.getProjectPath()
            , MainWindowView.getResourceString("select.directory.create"));
        File file = new File(pathToProject + ProviderSettings.getProjectSettingsMapValue(Settings.Keys.PROJECT_NAME));
        boolean isExistProject = true;
        if (file.exists()) {
            isExistProject = AlertDialog.getAlertOkCancel(
                MainWindowView.getResourceString("project.already.exist.title")
                , MainWindowView.getResourceString("project.already.exist.massage")
                , MainWindowView.getResourceString("continue")
                , MainWindowView.getResourceString("cancel")
            );
        }

        if (!isExistProject) {
            return;
        }

        CreateProjectTask createProjectTask =new CreateProjectTask(projectManager, file);
        CreateProjectCallBack createProjectCallBack = new CreateProjectCallBack();

        createProjectTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState==Worker.State.SUCCEEDED) {
                    createProjectCallBack.call(newState,createProjectTask.getException());
                }
            }
        );
        new Thread(createProjectTask).start();
    }



}
