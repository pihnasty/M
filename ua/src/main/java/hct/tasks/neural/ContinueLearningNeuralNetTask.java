package hct.tasks.neural;

import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class ContinueLearningNeuralNetTask extends BaseTask {

    public ContinueLearningNeuralNetTask(ProjectManager projectManager) {
        super(projectManager);
        LoggerP.write(Level.INFO, "ContinueLearningNeuralNetTask started...");
    }

    @Override
    protected Void call() {
        projectManager.continueLearningNeuralNet();
        LoggerP.write(Level.INFO, "ContinueLearningNeuralNetTask finished...");
        return null;
    }

}
