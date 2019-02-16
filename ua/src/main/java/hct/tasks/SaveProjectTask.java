package hct.tasks;

import javafx.concurrent.Task;
import logging.LoggerP;
import main.ProjectManager;
import mainwindows.MainWindowView;
import settings.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Formatter;
import java.util.logging.Level;

public class SaveProjectTask extends Task<Void> {

    private ProjectManager projectManager;

    public SaveProjectTask(ProjectManager projectManager) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "ProjectTask started...");
    }

    @Override
    protected Void call() {
        projectManager.saveProject();
        return null;
    }
}
