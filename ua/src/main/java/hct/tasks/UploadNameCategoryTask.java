package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;

import java.util.logging.Level;

public class UploadNameCategoryTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public UploadNameCategoryTask(ProjectManager projectManager, String pathToProject) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "UploadNameCategoryTask started...");
        this.pathToProject=pathToProject;
    }

    @Override
    protected Void call() {
        projectManager.uploadNameCategory(pathToProject);
        return null;
    }
}
