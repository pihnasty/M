package mainwindows;


import designpatterns.MVC;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import linechart.DataP;
import linechart.LineChartController;
import linechart.LineChartModel;
import linechart.LineChartP;
import main.AppProject;
import menu.MenuController;
import menu.MenuModel;
import menu.MenuView;
import persistence.loader.XmlRW;
import javafx.scene.control.ScrollPane;



public class MainWindowView extends BorderPane {

    public  static FXMLLoader loaderRecource;

    public MainWindowView() {

        loaderRecource = XmlRW.fxmlLoad(this,this, "mainwindows/mainwindowview.fxml","ui","");

        AppProject project = AppProject.getInstance();

        MVC menu = new MVC(MenuModel.class, MenuController.class, MenuView.class, project,null );
        this.setTop((MenuView)menu.getView());

        DataP dataP = new DataP();

        MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, dataP, null );

        //this.setCenter((LineChartP)LineChart1MVC.getView());

        MVC LineChart1MVC2  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, dataP, null );

        MVC LineChart1MVC3  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, dataP, null );
        MVC LineChart1MVC4  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, dataP, null );
        MVC LineChart1MVC5  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class, dataP, null );

        VBox vBox = new VBox();
        vBox.getChildren().add((LineChartP)LineChart1MVC.getView());
        vBox.getChildren().add((LineChartP)LineChart1MVC2.getView());
        vBox.getChildren().add((LineChartP)LineChart1MVC3.getView());
        vBox.getChildren().add((LineChartP)LineChart1MVC4.getView());
        vBox.getChildren().add((LineChartP)LineChart1MVC5.getView());

       // this.setCenter(vBox);

        ScrollPane scrollPane = new ScrollPane();



        // Set content for ScrollPane
        scrollPane.setContent(vBox);

        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        this.setCenter(scrollPane);
    }
}
