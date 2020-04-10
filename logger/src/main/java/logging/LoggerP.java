package logging;

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
    private static String logfileName = "log-M-";

    public static FXMLLoader loader = new FXMLLoader();

    static {
        try {
            loader.setResources(ResourceBundle.getBundle("ui"));

            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh-mm-ss");
            Date date = new Date();
            boolean append = false;
            FileHandler fh = new FileHandler("D:\\A\\M\\log\\"+logfileName+format.format(date)+".log", append);
            //fh.setFormatter(new XMLFormatter());
            fh.setFormatter(new SimpleFormatter());
            logger = Logger.getLogger(logfileName);
            logger.addHandler(fh);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static interface Names {
        public static String LOGGER_M = "Logger-M";
    }

    public static void writeWithResource(Level level, String message) {
        logger.log(level,loader.getResources().getString(message));
    }

    public static void write(Level level, String message) {
        logger.log(level,message);
    }

}


//  LoggerP.logger.log(Level.SEVERE, "Starting application", "LoggerP");
//Error:error: pathspec 'pde/src/main/java/trestview/tasks/conveyorPDE/V?onConveyorPdeModel.java' did not match any file(s) known to git.
//        during executing git "C:\Program Files\Git\cmd\git.exe" -c core.quotepath=false checkout HEAD -- pde/src/main/java/trestview/tasks/conveyorPDE/VСonConveyorPdeModel.java