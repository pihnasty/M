package trestview.machinetest.module5;

import com.sun.javafx.geom.Shape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;

import javax.swing.text.Style;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Роман on 15.04.2016.
 */
public class Module5View extends HBox implements Observer {


    Module5Model module5Model;
    Module5Controller module5Controller;
    XYChart.Series<Number, Number> series;
//    XYChart.Series<Number, Number> seriesBar;
    ObservableList<String> ranks;
    LineChart<Number, Number> lineChart;
    final NumberAxis xAxis;
    final NumberAxis yAxis;


    public Module5View(Module5Model model, Module5Controller controller) {
        this.module5Model = model;
        this.module5Controller = controller;
        ranks = FXCollections.observableArrayList();
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        xAxis.setLabel(ResourceBundle.getBundle("ui").getString("time"));
        this.lineChart = new LineChart<Number, Number>(xAxis , yAxis);
        series = new XYChart.Series<>();
        series.setName(ResourceBundle.getBundle("ui").getString("meanSquareDeviation"));
//        seriesBar = new XYChart.Series<>();
        lineChart.getData().add(series);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendSide(Side.RIGHT);
        getChildren().addAll(lineChart);
    }

    private void redrawChart(List<Double> listData, List<Integer> listRanks) {
        series.getData().clear();
        for(int i = 0; i < listData.size(); i++) {
            series.getData().add(new XYChart.Data(i, listData.get(i)));
        }
        System.out.println("In redrawChart()M5");
    }



    @Override
    public void update(Observable o, Object arg) {
        if(o.getClass() == Module5Model.class) {
            Module5Model m5 = (Module5Model) o;
            redrawChart(m5.getStandardDeviations(), null);
        }
    }



}
