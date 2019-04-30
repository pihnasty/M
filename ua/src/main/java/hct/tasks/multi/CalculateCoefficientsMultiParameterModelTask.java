package hct.tasks.multi;

import hct.tasks.base.BaseTask;
import logging.LoggerP;
import main.ProjectManager;
import java.util.logging.Level;

public class CalculateCoefficientsMultiParameterModelTask extends BaseTask {

    public CalculateCoefficientsMultiParameterModelTask (ProjectManager projectManager) {
        super(projectManager);
        LoggerP.write(Level.INFO, "CalculateCoefficientsMultiParameterModelTask started...");
    }

    @Override
    protected Void call() {
        projectManager.calculateCoefficientsMultiParameterModel();
        return null;
    }

}
