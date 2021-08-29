package hct.tasks.neural;

import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class LearningNeuralNetTask extends BaseTask {

    public LearningNeuralNetTask(ProjectManager projectManager) {
        super(projectManager);
        LoggerP.write(Level.INFO, "LearningNeuralNetTask started...");
    }

    @Override
    protected Void call() {
        projectManager.learningNeuralNet(1,true);
        LoggerP.write(Level.INFO, "LearningNeuralNetTaskTask finished...");
        return null;
    }

}
