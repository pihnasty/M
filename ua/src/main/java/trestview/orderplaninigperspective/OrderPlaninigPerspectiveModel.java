package trestview.orderplaninigperspective;

import designpatterns.ObservableDS;
import trestview.table.tablemodel.abstracttablemodel.Rule;

/**
 * Created by pom on 07.02.2016.
 */
public class OrderPlaninigPerspectiveModel extends ObservableDS {

    public OrderPlaninigPerspectiveModel(ObservableDS observableDS, Rule rule)  {
        super(observableDS, rule);
    }
}
