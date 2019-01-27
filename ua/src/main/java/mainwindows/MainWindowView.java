package mainwindows;


import designpatterns.MVC;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import main.AppProject;
import menu.MenuController;
import menu.MenuModel;
import menu.MenuView;
import persistence.loader.XmlRW;

public class MainWindowView extends BorderPane {

    public  static FXMLLoader loaderRecource;

    public MainWindowView() {

        loaderRecource = XmlRW.fxmlLoad(this,this, "mainwindows/mainwindowview.fxml","ui","");

        AppProject project = AppProject.getInstance();

        MVC menu = new MVC(MenuModel.class, MenuController.class, MenuView.class, project,null );
        this.setTop((MenuView)menu.getView());



    }
}
