package trestview.tasks.conveyorPDE.v_constcontrol.band;

import designpatterns.InitializableDS;
import designpatterns.MVC;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.BorderPaneObserverDS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persistence.loader.XmlRW;
import trestview.linechart.LineChartController;
import trestview.linechart.LineChartModel;
import trestview.linechart.LineChartP;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class VConstTimeControlBandConveyorPdeView extends BorderPaneObserverDS {

    @FXML
    private Label label = new Label();
    private    List<Point2D.Double> listConT =null;
    private    List<Point2D.Double> listConS=null;

    public VConstTimeControlBandConveyorPdeView(ObservableDS observebleDS, InitializableDS initializableDS){
        super(observebleDS,initializableDS);
        inizilize(observableDS);
        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,initializableDS, "trestview/tasks/conveyorPDE/v_constcontrol/band/VConstTimeControlBandConveyorPdeView.fxml","ui", "trestview/tasks/conveyorPDE/v_constcontrol/band/vConstTimeControlBandConveyorPdeStyle.css");
     //   setStyle("-fx-background-color: red;");
     //        getStyleClass().add("vConConveyorPdeStyle");
     //     getStylesheets().add("barchartsample/Chart.css");

        List<StrategyVConstTimeControlBand> strategies = Arrays.asList(
                new StrategyVConstTimeControlBand01(),
                new StrategyVConstTimeControlBand02(),
                new StrategyVConstTimeControlBand03(),
                new StrategyVConstTimeControlBand04(),
                new StrategyVConstTimeControlBand05(),new StrategyVConstTimeControlBand06(),new StrategyVConstTimeControlBand07()
                );

//        List<StrategyVConstTimeControlBand> strategies = Arrays.asList(    new StrategyVConstTimeControlBand03()     );

        HBox hBoxBottom1 = new HBox();
        strategies.forEach(s-> {
            hBoxBottom1.getChildren().add(getButton((VConstTimeControlBandConveyorPdeModel) observebleDS, s));
        });
        setBottom(hBoxBottom1);
    }

    private Button getButton(VConstTimeControlBandConveyorPdeModel observebleDS, StrategyVConstTimeControlBand strategy) {
        Button buttonForStrategy = new Button();
        String buttonName = "button"+strategy.getClass().getSimpleName();
        String buttonTooltip = "button"+strategy.getClass().getSimpleName()+"Tooltip";
        buttonForStrategy.setText(ResourceBundle.getBundle("ui").getString(buttonName));
        buttonForStrategy.setTooltip(new Tooltip(ResourceBundle.getBundle("ui").getString(buttonTooltip)));
        buttonForStrategy.setOnAction( event -> observebleDS.setStrategy(strategy));
        return buttonForStrategy;
    }

    public void update(Observable observeble, Object arg) {
        inizilize((ObservableDS)observeble);
    }

    private void inizilize(ObservableDS observableDS) {
        int widthSection = (int)1.5* Toolkit.getDefaultToolkit ().getScreenSize ().width/3;

        MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConstTimeControlBandConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("sigm"), null );

        MVC LineChart1AMVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConstTimeControlBandConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("controlConstantSpeedBand"), null );

        MVC LineChart2MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConstTimeControlBandConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("control_sigma"), null );

        MVC LineChart3MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConstTimeControlBandConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("oX=X"), null );


        List<MVC> listMVCs = new ArrayList<>();
        for (int i=0; i<10; i++)  listMVCs.add( new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConstTimeControlBandConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("oX=Salone0"+i), null )
        );

        List<MVC> listMVCt = new ArrayList<>();
        for (int i=0; i<10; i++)  listMVCt.add( new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConstTimeControlBandConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("oX=Talone0"+i), null )
        );


        VBox vBoxLeft = new VBox();
        vBoxLeft.getChildren().addAll(
        (LineChartP)LineChart1MVC.getView(),
        (LineChartP)LineChart1AMVC.getView(),
        (LineChartP)LineChart2MVC.getView(),(LineChartP)LineChart3MVC.getView());
        setLeft(vBoxLeft);


        VBox vBoxCenterLeft = new VBox();
        VBox vBoxCenterRight = new VBox();
        int j=0;
        for (MVC mvc : listMVCs)
            if(j++ < 5) vBoxCenterLeft.getChildren().add((LineChartP)mvc.getView());
            else vBoxCenterRight.getChildren().add((LineChartP)mvc.getView());
        Label labelT = new Label(ResourceBundle.getBundle("ui").getString("titleSectionCenter"));
        labelT.setMinHeight(40);
        HBox hBoxCenter = new HBox(vBoxCenterLeft,vBoxCenterRight);
        hBoxCenter.setMaxWidth(widthSection);
        hBoxCenter.setMinWidth(widthSection-1);
        setCenter(new VBox (labelT,hBoxCenter));


        VBox vBoxRightLeft = new VBox();
        VBox vBoxRightRight = new VBox();
        j=0;
        for (MVC mvc : listMVCt)
            if(j++ < 5) vBoxRightLeft.getChildren().add((LineChartP)mvc.getView());
            else vBoxRightRight.getChildren().add((LineChartP)mvc.getView());


        Label labelS = new Label(ResourceBundle.getBundle("ui").getString("titleSectionRight"));
        labelS.setMinHeight(40);
        HBox hBoxRight = new HBox(vBoxRightLeft,vBoxRightRight);
        hBoxRight.setMaxWidth(widthSection);
        hBoxRight.setMinWidth(widthSection-1);
        setRight(new VBox (labelS, hBoxRight) );
    }
}