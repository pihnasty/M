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

public class LoggerP_test {
    public static Logger logger;
    public static String projectPath;
    public static String relativePath = "log";
    private static String logfileName = "log-Test";

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
        // logging only Level.SEVERE
        fh.setLevel(Level.INFO);
        logger.addHandler(fh);
        logger.setLevel(Level.INFO);   logger.setUseParentHandlers(false);

    }

}
