package menu;

import designpatterns.ObservableDS;
import entityProduction.*;
import persistence.loader.tabDataSet.*;


import java.util.Observable;

public class MenuModel extends ObservableDS {


    public MenuModel(ObservableDS observableDS, Rule rule) {
            super(observableDS, rule);
    }


    public void changed() {
        setChanged();
        notifyObservers();
    }

    public enum Rule {
        RowWork(persistence.loader.tabDataSet.RowWork.class),
        RowMachine(persistence.loader.tabDataSet.RowMachine.class),
        RowTypemachine(persistence.loader.tabDataSet.RowTypemachine.class),
        RowFunctiondist(persistence.loader.tabDataSet.RowFunctiondist.class),
        RowUnit(persistence.loader.tabDataSet.RowUnit.class),
        RowOperation (RowOperation.class),
        Work(entityProduction.Work.class),
        Machine(entityProduction.Machine.class),

        Subject_labour(entityProduction.Subject_labour.class),
        Route(entityProduction.Route.class),
        Lineroute(entityProduction.Lineroute.class),
        Linespec(Linespec.class),

        Order(Order.class),
        Line(Line.class),

        Functiondist(Functiondist.class),
        RoutePerspective(Route.class),
        Functiondist2(Functiondist.class);

        Rule(Class clazz) {
            this.clazz = clazz;
        }

        private Class clazz;

        public Class getClassTab() {
            return this.clazz;
        }
    }
}
