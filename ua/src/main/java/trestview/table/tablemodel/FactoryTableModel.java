package trestview.table.tablemodel;


import persistence.loader.DataSet;

/**
 * Created by pom on 05.03.2016.
 */

public class FactoryTableModel  extends AbstractFactoryTableModel {

    private DataSet dataSet;
    private Class tClass;


    public FactoryTableModel (DataSet dataSet, Class tClass) {
        this.dataSet =  dataSet;
        this.tClass = tClass;

    }

    public TableModel getTableModel() {
        return null;
    }


}
