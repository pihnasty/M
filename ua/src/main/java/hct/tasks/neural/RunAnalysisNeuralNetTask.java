package hct.tasks.neural;

import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class RunAnalysisNeuralNetTask extends BaseTask {

    public RunAnalysisNeuralNetTask(ProjectManager projectManager) {
        super(projectManager);
        LoggerP.write(Level.INFO, "RunAnalysisNeuralNetTaskTask started...");
    }

    @Override
    protected Void call() {
        projectManager.runDataAnalysisNeuralNet();
        LoggerP.write(Level.INFO, "RunAnalysisNeuralNetTaskTask finished...");
        return null;
    }

}
