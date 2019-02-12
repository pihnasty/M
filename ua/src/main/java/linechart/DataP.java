package linechart;

import designpatterns.ObservableDS;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataP extends ObservableDS implements LineChartInterface {

    private List<List<Point2D.Double>> listList = new ArrayList<>();

    private double xMax;
    private double xMin;
    private double yMax;
    private double yMin;

    private String titleX;
    private String titleY;

    private String titleGraph;
    private List<String> listLegend = new ArrayList<>();

    private double delta =0.05;

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
    public double getxMax() {
        return xMax + (xMax-xMin)*delta;
    }

    @Override
    public double getxMin() {
        return xMin - (xMax-xMin)*delta;
    }

    @Override
    public double getxTickUnit() {
        return 1;
    }

    @Override
    public double getyMax() {
        return yMax + (yMax-yMin)*delta;
    }

    @Override
    public double getyMin() {
        return yMin -(yMax-yMin)*delta;
    }

    @Override
    public double getyTickUnit() {
        return 1;
    }

    @Override
    public String getTitleY() {
        return titleY;
    }

    @Override
    public List<List<Point2D.Double>> getPullList() {
        return listList;
    }

    public void addList (List<Point2D.Double> list) {

        listList.add(list);
    }

    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    public void setyMax(double yMax) {
        this.yMax = yMax;
    }

    public void setyMin(double yMin) {
        this.yMin = yMin;
    }

    public void setTitleX(String titleX) {
        this.titleX = titleX;
    }

    public void setTitleY(String titleY) {
        this.titleY = titleY;
    }

    public void setTitleGraph(String titleGraph) {
        this.titleGraph = titleGraph;
    }

    public void setLegend(String listLegend) {
        this.listLegend.add(listLegend);
    }
}
