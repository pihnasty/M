package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class SeparatedRawDataTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public SeparatedRawDataTask(ProjectManager projectManager) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "SeparatedRawDataTask started...");

    }

    @Override
    protected Void call() {
        projectManager.separatedRawData();
        return null;
    }
}
