package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;

import java.io.FileNotFoundException;
import java.util.logging.Level;

public class DownloadExperimentPlanTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public DownloadExperimentPlanTask(ProjectManager projectManager, String pathToProject) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "DownloadExperimentPlanTask started...");
        this.pathToProject=pathToProject;
    }

    @Override
    protected Void call() throws FileNotFoundException {
        projectManager.downloadExperimentPlan(pathToProject);
        return null;
    }
}
