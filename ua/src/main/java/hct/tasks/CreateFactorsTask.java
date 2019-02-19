package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class CreateFactorsTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public CreateFactorsTask(ProjectManager projectManager) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "CreateFactorsTask started...");

    }

    @Override
    protected Void call() {
        projectManager.createFactors();
        return null;
    }
}
