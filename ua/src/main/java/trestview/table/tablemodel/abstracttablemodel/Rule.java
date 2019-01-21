package trestview.table.tablemodel.abstracttablemodel;

import entityProduction.*;
import persistence.loader.tabDataSet.*;

public enum Rule {
    RowWork(RowWork.class),
    RowMachine(RowMachine.class),
    RowTypemachine(RowTypemachine.class),
    RowFunctiondist(RowFunctiondist.class),
    RowUnit(RowUnit.class),
    RowOperation (RowOperation.class),
    Work(Work.class),
    Machine(Machine.class),

    Subject_labour(Subject_labour.class),
    Route(Route.class),
    Lineroute(Lineroute.class),
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

