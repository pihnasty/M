package trestview.machinetest;

import designpatterns.InitializableDS;
import designpatterns.MVC;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.BorderPaneObserverDS;
import entityProduction.Functiondist;
import javafx.geometry.Insets;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import trestview.machinetest.animation.ProcessingAnimationModel;
import trestview.machinetest.animation.ProcessingAnimationView;
import trestview.machinetest.charts.ChartCommonModel;
import trestview.machinetest.charts.ChartController;
import trestview.machinetest.charts.ChartView;
import trestview.machinetest.module0.Module0Model;
import trestview.machinetest.module3.*;
import trestview.machinetest.module4.*;
import trestview.machinetest.module5.*;
import trestview.table.TableController;
import trestview.table.TableViewP;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;


import java.util.*;

/**
 * Created by Roman Korsun on 22.03.2016.
 */
public class MachineTestView extends BorderPaneObserverDS implements Observer {
    private MachineTestModel machineTestModel;
    private TableViewP tableView;

//    LineChart<Number,Number> lineChart;
    ChartView chartView;
    ChartView chartView1;
    ChartView chartView2;

    XYChart.Series series;

//    public MachineTestView(MachineTestModel mTestModel, MachineTestController mTestController) {

    public MachineTestView(MachineTestModel model, MachineTestController controller) {
        this((ObservableDS)model, (InitializableDS)controller);
    }

    public MachineTestView(ObservableDS mTestModel, InitializableDS mTestController) {
        super(mTestModel, mTestController);
        this.machineTestModel = (MachineTestModel) mTestModel;
        XmlRW.fxmlLoad(this, mTestController,"trestview\\machinetest\\machinetestView.fxml", "ui", "");
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("machinetestView.fxml"));
//        fxmlLoader.setResources(ResourceBundle.getBundle("resources.ui"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(machineTestController);
//
//        try {
//            fxmlLoader.load();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }

//        setTitle(fxmlLoader.getResources().getString("TestOfMachine"));


        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setPadding(new Insets(10, 5, 5, 10));

        //img
//        Image image = new Image("file:Image\\animation\\presBot.png");

//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        imageView.fitWidthProperty().bind(widthProperty());

//        imageView.setPreserveRatio(true);

//-----------------------------------------------------------------------------------------------
        ArrayList<Functiondist> functiondists = new ArrayList<>();
        //   for (RowFunctiondist rowFun: dataSet.getTabFunctiondists()) functiondists.add(dataSet.createObject( rowFun));   // Это эквивалентно  dataSet.getTabFunctiondists().stream().filter(w->{functiondists.add(dataSet.createObject(w)); return true;}).count();
        machineTestModel.getDataSet().getTabFunctiondists().stream().filter(w->{functiondists.add(machineTestModel.getDataSet().createObject(w)); return true;}).count();
        DataSet.showTab(functiondists);
//        MVC tableMVC = new MVC(TableModel.class, TableController.class, TableViewP.class, functiondists, Rule.Functiondist);
//        tableView = (TableViewP) tableMVC.getView();
//--------------------------------------------------------------------------------------------------



        SplitPane splitPane = new SplitPane();

        //creating the chart
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis();

        final NumberAxis xAxis2 = new NumberAxis();
        final NumberAxis yAxis2 = new NumberAxis();
        //----------------------------------------------------//
        Module0Model module0Model = new Module0Model();

        ProcessingAnimationModel animationModel = new ProcessingAnimationModel();
        ProcessingAnimationView animationView = new ProcessingAnimationView(animationModel);
        module0Model.addObserver(animationModel);
        animationModel.addObserver(animationView);

        Module3Model module3Model = new Module3Model(module0Model);
        Module3Controller module3Controller = new Module3Controller(module3Model);
        Module3View module3View = new Module3View(module3Model, module3Controller);
        module0Model.addObserver(module3Model);
        module3Model.addObserver(module3View);

        Module4Model module4Model = new Module4Model(module0Model);
        Module4Controller module4Controller = new Module4Controller(module4Model);
        Module4View module4View = new Module4View(module4Model, module4Controller);
        module0Model.addObserver(module4Model);
        module4Model.addObserver(module4View);

        Module5Model module5Model = new Module5Model(module0Model);
        Module5Controller module5Controller = new Module5Controller(module5Model);
        Module5View module5View = new Module5View(module5Model, module5Controller);
        module0Model.addObserver(module5Model);
        module5Model.addObserver(module5View);

        module0Model.populateList(0);

//        ChartCommonModel chartCommonModel1 = new ChartCommonModel();
//        ChartController chartController1 = new ChartController(chartCommonModel1);
//        chartView1 = new ChartView(xAxis1, yAxis1, chartCommonModel1, chartController1);
//        machineTestModel.addObserver(chartView1);
//
//        ChartCommonModel chartCommonModel2 = new ChartCommonModel();
//        ChartController chartController2 = new ChartController(chartCommonModel2);
//        chartView2 = new ChartView(xAxis2, yAxis2, chartCommonModel2, chartController2);
//        machineTestModel.addObserver(chartView2);

        //---------------------------------------------------//

//        xAxis.setLabel("X");
        series = new XYChart.Series();
//        series.setName("Random values");    //Put in resourses


//        lineChart = new LineChart<Number, Number>(xAxis,yAxis);

        //populating the series with data
        populateSeries(machineTestModel.getRandomValuesList());

        VBox vBox1 = new VBox(5);
        vBox1.setSpacing(5);
//        chartView.setMaxSize(600, 400);
//        chartView1.setMaxSize(600, 400);
//        chartView2.setMaxSize(600, 400);
//        vBox = new VBox();
        vBox.getChildren().addAll(animationView);//add tableview
        vBox1.getChildren().addAll(module3View, module4View, module5View); //, chartView1, chartView2);
        splitPane.getItems().addAll(vBox, vBox1);

        setCenter(splitPane);
    }

    private void populateSeries(ArrayList<Double> list) {
        for(int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data(i, list.get(i)));
        }

//        chartView1.getData().add(series);
//        chartView1.getData().add(series);
//        chartView2.getData().add(series);
    }


    @Override
    public void update(Observable o, Object arg) {
        machineTestModel = (MachineTestModel)o;
    }
}
