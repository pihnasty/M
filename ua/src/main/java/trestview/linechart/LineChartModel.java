package trestview.linechart;

import designpatterns.ObservableDS;
import persistence.loader.DataSet;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.geom.Point2D;
import java.util.List;

public class LineChartModel extends ObservableDS implements LineChartInterface {


    private List<Point2D.Double> list;
    private List<String> listLegend;
    private List<List<Point2D.Double>> pullList;
    private String titleGraph;
    private String titleX;
    private String titleY;
    private double xMin;
    private double xMax;
    private double xTickUnit;
    private double yMin;
    private double yMax;
    private double yTickUnit;

    public LineChartModel(ObservableDS o, Rule rule){
       super(o,rule);
        list =( (LineChartInterface) observableDS).getList();
        listLegend = ( (LineChartInterface) observableDS).getListLegend();
        pullList =( (LineChartInterface) observableDS).getPullList();
        titleGraph=( (LineChartInterface) observableDS).getTitleGraph();
        titleX=( (LineChartInterface) observableDS).getTitleX();
        titleY=( (LineChartInterface) observableDS).getTitleY();
        xMin = ( (LineChartInterface) observableDS).getxMin();
        xMax = ( (LineChartInterface) observableDS).getxMax();
        yMin = ( (LineChartInterface) observableDS).getyMin();
        yMax = ( (LineChartInterface) observableDS).getyMax();
        xTickUnit = ( (LineChartInterface) observableDS).getxTickUnit();
        yTickUnit = ( (LineChartInterface) observableDS).getyTickUnit();
    }

    @Override
    public List<Point2D.Double> getList() {
        return list;
    }

    @Override
    public List<String> getListLegend() {
        return listLegend;
    }

    @Override
    public String getTitleGraph() {
        return titleGraph;
    }

    @Override
    public String getTitleX() {
        return titleX;
    }

     @Override
    public String getTitleY() {
        return titleY;
    }

    @Override
    public List<List<Point2D.Double>> getPullList() {
        return pullList;
    }
    @Override
    public double getxMax() {
        return xMax;
    }
    @Override
    public double getxMin() {
        return xMin;
    }

    @Override
    public double getxTickUnit() {
        return xTickUnit;
    }

    @Override
    public double getyMax() {
        return yMax;
    }

    @Override
    public double getyMin() {
        return yMin;
    }

    @Override
    public double getyTickUnit() {
        return yTickUnit;
    }

}
