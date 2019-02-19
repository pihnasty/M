package hct.handlers;

import designpatterns.ObservableDS;
import dialogs.AlertDialog;
import fio.FileUI;
import hct.callbacks.CreateProjectCallBack;
import hct.callbacks.UploadRawDataCallBack;
import hct.tasks.CreateProjectTask;
import hct.tasks.UploadRawDataTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;
import settings.ProviderSettings;
import settings.Settings;

import java.io.File;

public class UploadRawDataHandler {

    public void uploadRawData(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        String pathToFile = FileUI.getPathToFile(
            projectManager.getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("upload.raw.data")
        );

        UploadRawDataTask uploadRawDataTask = new UploadRawDataTask(projectManager, pathToFile);
        UploadRawDataCallBack uploadRawDataCallBack = new UploadRawDataCallBack();

        uploadRawDataTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    uploadRawDataCallBack.call(newState, uploadRawDataTask.getException());
                }
            }
        );
        new Thread(uploadRawDataTask).start();
    }


}
