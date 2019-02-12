package linechart;

import java.awt.geom.Point2D;
import java.util.List;

public interface LineChartInterface {
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
