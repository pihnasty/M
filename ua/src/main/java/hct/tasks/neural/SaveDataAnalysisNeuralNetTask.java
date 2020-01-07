package hct.tasks.neural;

import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class SaveDataAnalysisNeuralNetTask extends BaseTask {

    public SaveDataAnalysisNeuralNetTask(ProjectManager projectManager) {
        super(projectManager);
        LoggerP.write(Level.INFO, "SaveDataAnalysisNeuralNetTaskTask started...");
    }

    @Override
    protected Void call() {
        projectManager.saveDataAnalysisNeuralNet();
        LoggerP.write(Level.INFO, "SaveDataAnalysisNeuralNetTaskTask finished...");
        return null;
    }

}
