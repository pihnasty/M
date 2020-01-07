package hct.handlers.neural;

import designpatterns.ObservableDS;
import hct.callbacks.base.BaseCallBack;
import hct.callbacks.neural.UploadDataAnalysisNeuralNetCallBack;
import hct.handlers.base.BaseHandler;
import hct.tasks.base.BaseTask;
import hct.tasks.neural.UploadDataAnalysisNeuralNetTask;
import javafx.concurrent.Worker;
import main.ProjectManager;

import java.io.IOException;
import java.util.Objects;

public class UploadDataAnalysisNeuralNetHandler extends BaseHandler {

    public void uploadDataAnalysis(ObservableDS observableDS) throws IOException {


        BaseTask task = new UploadDataAnalysisNeuralNetTask((ProjectManager) observableDS);

        BaseCallBack callBack = new UploadDataAnalysisNeuralNetCallBack(
        "upload.data.analysis.neural.net.title"
        ,"upload.data.analysis.neural.net.failed");

        addCallBack(task, callBack);
        new Thread(task).start();
    }

    @Override
    protected void addCallBack(BaseTask task, BaseCallBack baseCallBack) {

        task.stateProperty().addListener(
            (observable, oldState, newState) -> {
                if (Objects.isNull(((UploadDataAnalysisNeuralNetTask)task).getFullFileName())) {
                    newState = Worker.State.FAILED;
                    baseCallBack.call(newState,new IOException("No file selected"));
                } else {
                    baseCallBack.call(newState, task.getException());
                }
            }
        );
    }

}

