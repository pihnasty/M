package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;
import mainwindows.MainWindowView;
import settings.ProviderSettings;
import settings.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Formatter;
import java.util.logging.Level;

public class UploadRawDataTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public UploadRawDataTask(ProjectManager projectManager, String pathToProject) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "UploadRawDataTask started...");
        this.pathToProject=pathToProject;
    }

    @Override
    protected Void call() {
        projectManager.uploadRawData(pathToProject);
        return null;
    }
}
