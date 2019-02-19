package hct.handlers;

import designpatterns.ObservableDS;
import fio.FileUI;
import hct.callbacks.UploadNameCategoryCallBack;
import hct.callbacks.UploadRawDataCallBack;
import hct.tasks.UploadNameCategoryTask;
import hct.tasks.UploadRawDataTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;

public class UploadNameCategoryHandler {

    public void uploadNameCategory(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        String pathToFile = FileUI.getPathToFile(
            projectManager.getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("upload.name.category")
        );

        UploadNameCategoryTask uploadNameCategoryTask = new UploadNameCategoryTask(projectManager, pathToFile);
        UploadNameCategoryCallBack uploadNameCategoryCallBack = new UploadNameCategoryCallBack();

        uploadNameCategoryTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    uploadNameCategoryCallBack.call(newState, uploadNameCategoryTask.getException());
                }
            }
        );
        new Thread(uploadNameCategoryTask).start();
    }


}
