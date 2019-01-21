package trestview.menu;

import designpatterns.ObservableDS;
import trestmodel.TrestModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;
import java.util.Observer;

public class TMenuModel extends ObservableDS {

    public MenuItemCall getMenuItemCall() { return menuItemCall;  }

    public void setMenuItemCall(MenuItemCall menuItemCall) { this.menuItemCall = menuItemCall; }

    private MenuItemCall menuItemCall = MenuItemCall.defaultItem;



    public TMenuModel(ObservableDS trestModel) {
        this.observableDS = trestModel;
    }

    public TMenuModel(ObservableDS observableDS, Rule rule) {
        super(observableDS,rule);

    }

    public TrestModel getTrestModel() {
        return (TrestModel) observableDS;
    }

    public void setTrestModel(Observable trestModel) {
        this.observableDS = (ObservableDS) trestModel;
        changed();
    }

    public void changed() {
        setChanged();
        notifyObservers();
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

}
