package mainwindows;


import com.sun.javafx.print.PrinterJobImpl;
import designpatterns.MVC;
import designpatterns.observerdsall.BorderPaneObserverDS;
import experiment.Plan;
import factors.Factor;
import factors.FactorManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import linechart.DataP;
import linechart.LineChartController;
import linechart.LineChartModel;
import linechart.LineChartP;
import main.AppProject;
import main.ProjectManager;
import math.MathP;
import menu.MenuController;
import menu.MenuModel;
import menu.MenuView;
import persistence.loader.XmlRW;
import javafx.scene.control.ScrollPane;
import settings.ProviderSettings;
import settings.Settings;

import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


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
