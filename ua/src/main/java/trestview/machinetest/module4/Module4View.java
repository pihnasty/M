package trestview.machinetest.module4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Роман on 15.04.2016.
 */
public class Module4View extends HBox implements Observer {


    Module4Model module4Model;
    Module4Controller module4Controller;
    XYChart.Series<Number, Number> series;
    XYChart.Series<Number, Number> series2;
    XYChart.Series<Number, Number> series3;
    XYChart.Series<Number, Number> series4;
    LineChart<Number, Number> lineChart;
    LineChart<Number, Number> lineChart2;
    final NumberAxis xAxis;
    final NumberAxis yAxis;

    final NumberAxis xAxis2;
    final NumberAxis yAxis2;

    public Module4View(Module4Model model, Module4Controller controller) {
        this.module4Model = model;
        this.module4Controller = controller;
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        xAxis2 = new NumberAxis();
        yAxis2 = new NumberAxis();
        xAxis.setLabel(ResourceBundle.getBundle("ui").getString("time"));
        xAxis2.setLabel(ResourceBundle.getBundle("ui").getString("time"));
        this.lineChart = new LineChart<Number, Number>(xAxis , yAxis);
        this.lineChart2 = new LineChart<Number, Number>(xAxis2 , yAxis2);
        series = new XYChart.Series<>();
        series2 = new XYChart.Series<>();
        series3 = new XYChart.Series<>();
        series4 = new XYChart.Series<>();
        lineChart.getData().addAll(series);//, series2);
        lineChart2.getData().addAll(series3);//, series4);
        lineChart.setAnimated(false);
        lineChart2.setAnimated(false);
        lineChart.setCreateSymbols(false);
        lineChart2.setCreateSymbols(false);

//        lineChart.setTitle();
        series3.setName(ResourceBundle.getBundle("ui").getString("temp"));
        series.setName(ResourceBundle.getBundle("ui").getString("averageValue"));
        getChildren().addAll(lineChart, lineChart2);

    }

    private void redrawChart(Chart chart, List<Double> listData, List<Double> listTime, double constant,
                             XYChart.Series series1, XYChart.Series series2) {
        series1.getData().clear();

        for(int i = 0; i < listData.size(); i++) {
//            series2.getData().add(new XYChart.Data(i, constant));
            series1.getData().add(new XYChart.Data(i, listData.get(i)));
        }
        System.out.println("In redrawChart()M4");
    }




    @Override
    public void update(Observable o, Object arg) {
        if(o.getClass() == Module4Model.class) {
            Module4Model m4 = (Module4Model) o;
            redrawChart(lineChart, m4.getExpectedValues(), null, m4.getConstantVal(), series, null); //series2);
            redrawChart(lineChart2, m4.getTemps(), null, m4.getConstantVal(), series3, null); // series4);
        }
    }



}
