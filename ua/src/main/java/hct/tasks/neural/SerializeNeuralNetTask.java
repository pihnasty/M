package hct.tasks.neural;

import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class SerializeNeuralNetTask extends BaseTask {

    public SerializeNeuralNetTask(ProjectManager projectManager) {
        super(projectManager);
        LoggerP.write(Level.INFO, "SerializeNeuralNetTask started...");
    }

    @Override
    protected Void call() {
        projectManager.serializeNeuralNet();
        LoggerP.write(Level.INFO, "SerializeNeuralNetTask finished...");
        return null;
    }

}
