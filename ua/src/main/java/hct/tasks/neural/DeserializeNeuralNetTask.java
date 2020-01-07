package hct.tasks.neural;

import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class DeserializeNeuralNetTask extends BaseTask {

    public DeserializeNeuralNetTask(ProjectManager projectManager) {
        super(projectManager);
        LoggerP.write(Level.INFO, "DeserializeNeuralNetTask started...");
    }

    @Override
    protected Void call() {
        projectManager.deserializeNeuralNet();
        LoggerP.write(Level.INFO, "DeserializeNeuralNetTask finished...");
        return null;
    }

}
