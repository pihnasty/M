package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logging.LoggerP;
import mainwindows.MainWindowView;

import java.awt.*;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * IDEA 2017.1.2  Oracle VM
 */
public class AppM extends Application {
    /**
     * To start  launch(args) you need:
     * 1.Explicitly create a default constructor (no arguments);
     * 2.Create a constructor with arguments and call it method.
     */
    public final int DEFAULT_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final int DEFAULT_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;



    public static void main(String[] args) {
            launch();
    }


    @Override
    public void start(Stage stage) throws Exception {

        LoggerP.logger.log(Level.SEVERE, "Starting application", "LoggerP");

  //      MVC trestMVC = new MVC(TrestModel.class, TrestController.class, TrestView.class, new ObservableDS(), null);

 //       MVC menu = new MVC(TMenuModel.class, TMenuController.class, TMenuView.class, null,null );
        //endregion

        //region Methods of specifying the icon.
        //  1.------------------------------------------
        //      Image im = new Image(this.getClass().getResource("../resources/images/icons/work3.png").toString());
        //      Image(IconT.class.getResource("work3.png").toString())
        //      ----------------------------------------
        //  2.  ImageView imv = new ImageView(im);
        //  3.  stage.getIcons().add(imv);
        stage.getIcons().add(new Image("file:pde\\src\\main\\resources\\images\\icons\\work3.png"));

        //endregion


        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("ui"));
        stage.setTitle(loader.getResources().getString("stageTitle"));
        stage.initStyle(StageStyle.DECORATED);
        stage.setMaximized(true);
        stage.setScene(new Scene(new MainWindowView()));
        stage.setWidth(DEFAULT_WIDTH / 2);
        stage.setHeight(DEFAULT_HEIGHT / 2);
        stage.show();
    }
}
