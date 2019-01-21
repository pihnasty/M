package trestview.machinetest;

import designpatterns.MVC;
import designpatterns.ObservableDS;
import entityProduction.Functiondist;
import persistence.loader.DataSet;
import persistence.loader.tabDataSet.*;
import trestview.menu.TMenuModel;
import trestview.table.TableController;
import trestview.table.TableViewP;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Roman Korsun on 22.03.2016.
 */
public class MachineTestModel extends ObservableDS {

    private TMenuModel menuModel;
    private ArrayList<Double> randomValuesList;
    private DataSet dataSet;

//    private Class tClass;
    public MachineTestModel() {}

    public  MachineTestModel(ObservableDS observableDS, Rule rule) {
        super(observableDS, rule);
        this.randomValuesList = new ArrayList<>();
        this.dataSet = observableDS.getDataSet();
    }
//    public MachineTestModel() {
//        this.randomValuesList = new ArrayList<>();
//    }
//    public MachineTestModel(TMenuModel menuModel) {
//        this.menuModel = menuModel;
//        this.randomValuesList = new ArrayList<Double>();
//        populateList(100);
////        this.tClass = tClass;
//        this.dataSet =  menuModel.getTrestModel().getDataSet();
//
//
//
//    }

    public TMenuModel getTMenuModel() {
        return menuModel;
    }

    public void setTMenuModel(TMenuModel menuModel) {
        this.menuModel = menuModel;
        changed();
    }

    public ArrayList<Double> getRandomValuesList() {
        return randomValuesList;
    }

    public void setRandomValuesList(ArrayList<Double> randomValuesList) {
        this.randomValuesList = randomValuesList;
    }


    //adds a value to the List
    public void addValue(double value) {
        this.randomValuesList.add(value);
    }

    //removes value from List
    public void removeValue(int index) {
        this.randomValuesList.remove(index);
    }

    //populate the List with a test data
    private void populateList(int limit) {
        for (double i = 0; i < limit; i++) {
            randomValuesList.add(Math.atan(i));
        }
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }



    public DataSet getDataSet() {
        return dataSet;
    }
}
