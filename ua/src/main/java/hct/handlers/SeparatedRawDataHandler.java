package hct.handlers;

import designpatterns.ObservableDS;
import fio.FileUI;
import hct.callbacks.SeparatedRawDataCallBack;
import hct.callbacks.UploadRawDataCallBack;
import hct.tasks.SeparatedRawDataTask;
import hct.tasks.UploadRawDataTask;
import javafx.concurrent.Worker;
import main.ProjectManager;
import mainwindows.MainWindowView;

public class SeparatedRawDataHandler {

    public void separatedRawData(ObservableDS observableDS) {
        ProjectManager projectManager = ((ProjectManager) observableDS);

        SeparatedRawDataTask separatedRawDataTask = new SeparatedRawDataTask(projectManager);
        SeparatedRawDataCallBack separatedRawDataCallBack = new SeparatedRawDataCallBack();

        separatedRawDataTask.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED || newState == Worker.State.FAILED) {
                    separatedRawDataCallBack.call(newState, separatedRawDataTask.getException());
                }
            }
        );
        new Thread(separatedRawDataTask).start();
    }


}
