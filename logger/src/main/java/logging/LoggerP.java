package logging;

import io.file.Paths;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerP {
    public static Logger logger;
    public static String projectPath;
    public static String relativePath = "log";
    private static String logfileName = "log-P";

    public static FXMLLoader loader = new FXMLLoader();

    static {
        loader.setResources(ResourceBundle.getBundle("ui"));
    }

    public static void writeWithResource(Level level, String message) {
        logger.log(level,loader.getResources().getString(message));
    }

    public static void write(Level level, String message) {
        logger.log(level,message);
    }

    public static void setProjectPath(String projectPathNew) {
        projectPath = projectPathNew;


        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh-mm-ss");
        Date date = new Date();
        boolean append = false;
        FileHandler fh = null;
        String fullPath = projectPath+"\\"+relativePath+"\\"+logfileName;
        Paths.createPathIfNotExist(fullPath);
        try {
            fh = new FileHandler(fullPath + "\\" + logfileName + "-" + format.format(date) + ".log", append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //fh.setFormatter(new XMLFormatter());
        fh.setFormatter(new SimpleFormatter());
        logger = Logger.getLogger(logfileName);
        logger.addHandler(fh);
    }

}


//  LoggerP.logger.log(Level.SEVERE, "Starting application", "LoggerP");
//Error:error: pathspec 'pde/src/main/java/trestview/tasks/conveyorPDE/V?onConveyorPdeModel.java' did not match any file(s) known to git.
//        during executing git "C:\Program Files\Git\cmd\git.exe" -c core.quotepath=false checkout HEAD -- pde/src/main/java/trestview/tasks/conveyorPDE/VСonConveyorPdeModel.java