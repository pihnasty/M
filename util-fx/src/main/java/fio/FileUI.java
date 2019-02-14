package fio;

import javafx.fxml.FXMLLoader;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class FileUI {

    /**
     * This will select  the directory where we will create a database
     * @return	The directory path to the database
     */
//     public static String getPathToProject( String pathToProject, String title)
//    {
//        DirectoryChooser chooser=new DirectoryChooser();
//        chooser.setInitialDirectory(new File( pathToProject  )); // we set the current directory, which is defined in the config.xml
//        chooser.setTitle(title);
//        chooser.setInitialDirectory(new File( pathToProject));  //  .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        File file=chooser.showDialog(new Stage());
//        if (Objects.nonNull(file)) {
//            chooser.setInitialDirectory(file);
//        }
//
//        return getCanonicalPath(pathToProject, chooser.getInitialDirectory());
//    }

    public static String getPathToProject( String pathToProject, String title) {
        FileChooser chooser=new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("project","*.project");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle(title);
        chooser.setInitialDirectory(new File( pathToProject));  //
        File file=chooser.showOpenDialog(new Stage());
        if (Objects.nonNull(file)) {
            chooser.setInitialDirectory(file.getParentFile());
        }
        return getCanonicalPath(pathToProject, chooser.getInitialDirectory());
    }

    private static String getCanonicalPath(String pathToProject, File initialDirectory) {
        try {
            pathToProject = initialDirectory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  pathToProject.replace("\\", "//");
    }

    public static String getPathToFile( String pathToProject, String title)
    {
        FileChooser chooser=new FileChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(new File( pathToProject));  //  .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // we set the current directory, which is defined in the config
        File file=chooser.showOpenDialog(new Stage());
        if (Objects.nonNull(file)) {
            chooser.setInitialDirectory(file);
            return  getCanonicalPath(pathToProject, chooser.getInitialDirectory());
        }

        return  null;
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

    public static String getShortFileName (String canonicalPath) {
        String [] arrayAfterSplit = canonicalPath.split("//");
        return arrayAfterSplit[arrayAfterSplit.length-1];
    }

    public static String getPathToDirictory (String canonicalPath) {
        String [] arrayAfterSplit = canonicalPath.split("//");
        String fileName = arrayAfterSplit[arrayAfterSplit.length-1];
        return canonicalPath.replace("//"+fileName,"");
    }



}
