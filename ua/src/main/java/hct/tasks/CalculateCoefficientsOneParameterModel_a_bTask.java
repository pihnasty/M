package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class CalculateCoefficientsOneParameterModel_a_bTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public CalculateCoefficientsOneParameterModel_a_bTask(ProjectManager projectManager) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "CalculateCoefficientsOneParameterModel_a_bTask started...");

    }

    @Override
    protected Void call() {
        projectManager.calculateCoefficientsOneParameterModel_a_b();
        return null;
    }
}
