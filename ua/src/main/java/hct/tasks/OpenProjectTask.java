package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;

import java.io.FileNotFoundException;
import java.util.logging.Level;

public class OpenProjectTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public OpenProjectTask(ProjectManager projectManager, String pathToProject) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "OpenProjectTask started...");
        this.pathToProject=pathToProject;
    }

    @Override
    protected Void call() throws FileNotFoundException {
        projectManager.openProject(pathToProject);
        return null;
    }
}
