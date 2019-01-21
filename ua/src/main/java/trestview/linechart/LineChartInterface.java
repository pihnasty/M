package trestview.linechart;

import designpatterns.ObservableDS;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Observable;

 public interface LineChartInterface {
     List<Point2D.Double> getList();
     List<String> getListLegend();
     String getTitleGraph();
     String getTitleX();
     double getxMax();
     double getxMin();
     double getxTickUnit();
     double getyMax();
     double getyMin();
     double getyTickUnit();
     String getTitleY();
     List<List<Point2D.Double>> getPullList();

}
