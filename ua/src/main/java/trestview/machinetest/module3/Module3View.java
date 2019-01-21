package trestview.machinetest.module3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Роман on 15.04.2016.
 */
public class Module3View extends HBox implements Observer {


    Module3Model module3Model;
    Module3Controller module3Controller;
    XYChart.Series<Number, Number> series;
    XYChart.Series<Number, Number> seriesBar;
    ObservableList<String> ranks;
    LineChart<Number, Number> lineChart;
    BarChart barChart;//<String, Number> --> <Number, Number>
    final NumberAxis xAxis;
    final NumberAxis yAxis;
    final CategoryAxis xAxis1;
    final NumberAxis yAxis1;


    public Module3View(Module3Model model, Module3Controller controller) {

        ranks = FXCollections.observableArrayList();
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        xAxis1 = new CategoryAxis();
        yAxis1 = new NumberAxis();
//        yAxis1.setAutoRanging(false);
//        yAxis1.setLowerBound(0);
//        yAxis1.setUpperBound(10);
//        yAxis1.setTickUnit(3);

        this.lineChart = new LineChart<Number, Number>(xAxis , yAxis);
        this.barChart = new BarChart(xAxis1, yAxis1);
        this.module3Model = model;
        this.module3Controller = controller;
        series = new XYChart.Series<>();
        seriesBar = new XYChart.Series<>();
//        redrawChart();
        lineChart.getData().add(series);
        lineChart.setAnimated(false);
        barChart.getData().add(seriesBar);
        getChildren().addAll(lineChart, barChart);
        barChart.setAnimated(false);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);
        lineChart.setCreateSymbols(false);
        series.setName(ResourceBundle.getBundle("ui").getString("densityDistribution"));
//        seriesBar.setName(ResourceBundle.getBundle("ui").getString("statisticalDistribution"));
        seriesBar.setName("Гистограмма");

    }

    private void redrawChart(List<Double> listData, List<Integer> listRanks) {
        series.getData().clear();
        for(int i = 0; i < listRanks.size(); i++) {
            series.getData().add(new XYChart.Data(listRanks.get(i), listData.get(i)));
        }
        System.out.println("In redrawChart()");
    }

    private void redrawBarChart(List<Double> listData, List<Integer> listRanks) {
//        seriesBar.getData().clear();
        for (int i = 1; i <listRanks.size(); i++) {
            seriesBar.getData().add(new XYChart.Data(
                    listRanks.get(i).toString(),
                    listData.get(i)));
        }
        System.out.println("In redrawBarChart()M3");
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o.getClass() == Module3Model.class) {
            Module3Model m3 = (Module3Model) o;
            redrawChart(m3.getNormStatisticalSeries(), m3.getRanks());
            redrawBarChart(m3.getGroupedStatisticalSeries(), m3.getRanks());
        }
    }



}
