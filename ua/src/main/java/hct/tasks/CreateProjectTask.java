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

public class CreateProjectTask extends Task<Void> {

    private ProjectManager projectManager;
    private File file;

    public CreateProjectTask(ProjectManager projectManager, File file) {
        this.projectManager=projectManager;
        this.file=file;
        LoggerP.write(Level.INFO, "CreateProjectTask started...");
    }

    @Override
    protected Void call() throws Exception {
        String projectPath = file.getParent();
//        Paths.deleteDirectory(new File(projectPath));
        Path path = java.nio.file.Paths.get(projectPath);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
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

        projectManager.getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH, projectPath.replace("\\","//"));
        projectManager.saveProject();
        try {
            LoggerP.write(Level.INFO, MainWindowView.getResourceString("Create.new.project")+file.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
