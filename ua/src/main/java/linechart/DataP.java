package linechart;

import designpatterns.ObservableDS;
import entityProduction.Line;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataP extends ObservableDS implements LineChartInterface {
    @Override
    public List<Point2D.Double> getList() {
        List<Point2D.Double> list = new ArrayList<>();
        list.add(new Point2D.Double(0.0,0.0));
        list.add(new Point2D.Double(1.0,10.0));
        list.add(new Point2D.Double(2.0,20.0));

        return list;
    }

    @Override
    public List<String> getListLegend() {
        List<String> list = Arrays.asList("getListLegend1","getListLegend2","getListLegend3");
        return list ;
    }

    @Override
    public String getTitleGraph() {
        return "getTitleGraph";
    }

    @Override
    public String getTitleX() {
        return "getTitleX";
    }

    @Override
    public double getxMax() {
        return 5;
    }

    @Override
    public double getxMin() {
        return 0;
    }

    @Override
    public double getxTickUnit() {
        return 1;
    }

    @Override
    public double getyMax() {
        return 50;
    }

    @Override
    public double getyMin() {
        return 0;
    }

    @Override
    public double getyTickUnit() {
        return 1;
    }

    @Override
    public String getTitleY() {
        return "getTitleY()";
    }

    @Override
    public List<List<Point2D.Double>> getPullList() {
        List<Point2D.Double> list = new ArrayList<>();
        list.add(new Point2D.Double(0.0,0.0));
        list.add(new Point2D.Double(1.0,10.0));
        list.add(new Point2D.Double(2.0,20.0));
        list.add(new Point2D.Double(3.0,20.0));
        list.add(new Point2D.Double(5.0,20.0));

        List<Point2D.Double> list2 = new ArrayList<>();
        list2.add(new Point2D.Double(0.0,0.0));
        list2.add(new Point2D.Double(1.0,20.0));
        list2.add(new Point2D.Double(2.0,30.0));
        list2.add(new Point2D.Double(3.0,40.0));
        list2.add(new Point2D.Double(5.0,50.0));

        List<Point2D.Double> list3 = new ArrayList<>();
        list3.add(new Point2D.Double(0.0,0.0));
        list3.add(new Point2D.Double(1.0,30.0));
        list3.add(new Point2D.Double(2.0,40.0));
        list3.add(new Point2D.Double(3.0,50.0));
        list3.add(new Point2D.Double(5.0,60.0));

        List<List<Point2D.Double>> listList = new ArrayList<>();
        listList.add(list);
        listList.add(list2);
        listList.add(list3);

        return listList;
    }
}
