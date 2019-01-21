package trestview.routeperspective;

import designpatterns.ObservableDS;
import entityProduction.Trest;
import persistence.loader.DataSet;
import persistence.loader.SectionDataSet;
import trestmodel.TrestModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;

/**
 * Created by pom on 07.02.2016.
 */
public class RoutePerspectiveModel extends ObservableDS {

    public RoutePerspectiveModel (ObservableDS observableDS, Rule rule)  {
        super(observableDS, rule);
    }
}
