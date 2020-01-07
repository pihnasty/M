package hct.tasks.neural;

import fio.FileUI;
import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;
import mainwindows.MainWindowView;

import java.io.IOException;
import java.util.logging.Level;

public class UploadDataAnalysisNeuralNetTask extends BaseTask {

    private String fullFileName;

    public UploadDataAnalysisNeuralNetTask(ProjectManager projectManager) throws IOException {
        super(projectManager);
        String pathToFile = FileUI.getPathToFile(
            projectManager.getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("upload.data.analysis.neural.net.title")
        );
        this.fullFileName=pathToFile;
        LoggerP.write(Level.INFO, "UploadDataAnalysisNeuralNetTaskTask started...");
    }

    @Override
    protected Void call() {
        projectManager.uploadDataAnalysisNeuralNet(fullFileName);
        LoggerP.write(Level.INFO, "UploadDataAnalysisNeuralNetTaskTask finished...");
        return null;
    }

    public String getFullFileName() {
        return fullFileName;
    }
}

