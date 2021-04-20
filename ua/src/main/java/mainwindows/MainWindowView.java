package mainwindows;


import designpatterns.MVC;
import designpatterns.observerdsall.BorderPaneObserverDS;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import main.AppProject;
import main.ProjectManager;
import menu.MenuController;
import menu.MenuModel;
import menu.MenuView;
import persistence.loader.XmlRW;

import java.util.Observable;


public class MainWindowView extends BorderPaneObserverDS {

    public  static FXMLLoader loaderRecource;
    ProjectManager projectManager;

    public MainWindowView() {
        super(null,null);

        loaderRecource = XmlRW.fxmlLoad(this,this, "mainwindows/mainwindowview.fxml","ui","");

        projectManager = ProjectManager.getInstance();
        AppProject project =AppProject.getInstance();
        projectManager.setProject(project);
        MVC menu = new MVC(MenuModel.class, MenuController.class, MenuView.class, projectManager,null );
        this.setTop((MenuView)menu.getView());
        ((MenuModel) menu.getModel()).addObserver(this);
        projectManager.addObserver(this);


    }

    @Override
    public void update(Observable o, Object arg) {
        if("changeScrollPane".equalsIgnoreCase(arg.toString())) {
     //       setCenter((Node)projectManager.getScrollPane());
            ScrollPane scrollPane =  projectManager.getFunctionScrollPane().apply(false);
            scrollPane.setStyle("-fx-background-color:blue");
            setCenter(scrollPane);
        }
        if("cleanScrollPane".equalsIgnoreCase(arg.toString())) {
            setCenter(new ScrollPane());
        }
    }

    public static String getResourceString(String key) {
        return loaderRecource.getResources().getString(key);
    }
}
