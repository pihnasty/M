package hct.tasks;

import fio.FileUI;
import javafx.concurrent.Task;
import logging.LoggerP;
import main.AppProject;
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

public class SaveAsProjectTask extends Task<Void> {

    private ProjectManager projectManager;
    private String pathToProject;

    public SaveAsProjectTask(ProjectManager projectManager, String pathToProject) {
        this.projectManager=projectManager;
        LoggerP.write(Level.INFO, "SaveProjectTask started...");
        this.pathToProject=pathToProject;
    }

    @Override
    protected Void call() {
        try {
            File file = new File(pathToProject + ProviderSettings.getProjectSettingsMapValue(Settings.Keys.PROJECT_NAME));
            LoggerP.write(Level.INFO, "File name  "+ file.getCanonicalPath());
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(MainWindowView.getResourceString("name.project"));
            bw.newLine();
            Calendar calendar = Calendar.getInstance();

            Formatter formatter = new Formatter();
            formatter.format("%tc", calendar);
            bw.write(formatter.toString());

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        projectManager.saveAsProject(pathToProject);
        return null;
    }
}
