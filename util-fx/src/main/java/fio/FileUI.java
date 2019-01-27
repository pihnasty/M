package fio;

import javafx.fxml.FXMLLoader;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class FileUI {

    /**
     * This will select  the directory where we will create a database
     * @return	The directory path to the database
     */
     public static String getPathToProject( String pathToProject, String title)
    {
        DirectoryChooser chooser=new DirectoryChooser();
        chooser.setInitialDirectory(new File( pathToProject  )); // we set the current directory, which is defined in the config.xml
        chooser.setTitle(title);
        chooser.setInitialDirectory(new File( pathToProject));  //  .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File file=chooser.showDialog(new Stage());
        if (file != null) {
            chooser.setInitialDirectory(file);
            try {
                pathToProject=chooser.getInitialDirectory().getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pathToProject.replace("\\","//");
    }

    /**
     * Выбираем каталог, откуда будем записать данные БД
     * @return	выбранная через showDialog(...) директория с базой данных
     */
    static public String getSavePath(String pathToProject, String title)
    {
        DirectoryChooser chooser=new  DirectoryChooser();
        chooser.setInitialDirectory(new File( pathToProject  )); // we set the current directory, which is defined in the config.xml
        chooser.setTitle(title);
        File file = chooser.showDialog(new Stage());
        if (file != null) pathToProject = file.getAbsolutePath()+"\\";
        return pathToProject.replace("\\","//");
    }




}
