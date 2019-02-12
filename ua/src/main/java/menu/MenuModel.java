package menu;

import designpatterns.ObservableDS;
import entityProduction.*;
import persistence.loader.tabDataSet.*;


import java.util.Observable;

public class MenuModel extends ObservableDS {


    public MenuModel(ObservableDS observableDS, Rule rule) {
            super(observableDS, rule);
    }



    public MenuItemCall getMenuItemCall() { return menuItemCall;  }

    public void setMenuItemCall(MenuItemCall menuItemCall) { this.menuItemCall = menuItemCall; }

    private MenuItemCall menuItemCall = MenuItemCall.DEFAULT;




    public void changed() {
        setChanged();
        notifyObservers();
    }

    public void buildRawDataGraph() {
        this.menuItemCall=MenuItemCall.BUILD_RAW_DATA_GRAPH;
        changed();
    }

    public void defaultAction() {
        this.menuItemCall=MenuItemCall.DEFAULT;
        changed();
    }



    public void clickTestOfMachineItem() {
        this.menuItemCall = MenuItemCall.testOfMachineItem;
        changed();
    }

    public void clickResourcesLinksPerspectiveItem() {
        this.menuItemCall = MenuItemCall.resourcesLinksPerspectiveItem;
        changed();
    }


    public void clickConveyorSpeedConstantItem()  {
        this.menuItemCall = MenuItemCall.conveyorSpeedConstantItem;
        changed();
    }

    public void clickConveyorSpeedConstantControlBandItem()  {
        this.menuItemCall = MenuItemCall.conveyorSpeedConstantControlBandItem;
        changed();
    }

    public void clickConveyorSpeedDependsTimeItem()  {
        this.menuItemCall = MenuItemCall.conveyorSpeedDependsTimeItem;
        changed();
    }

    public void clickRoutePerspectiveItem()  {
        this.menuItemCall = MenuItemCall.routePerspectiveItem;
        changed();
    }

    public void clickOrderPlaninigPerspectiveItem()  {
        this.menuItemCall = MenuItemCall.orderPlaninigPerspectiveItem;
        changed();
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
