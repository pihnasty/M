package trestview.table.tablemodel;

import designpatterns.ObservableDS;
import entityProduction.Trest;
import entityProduction.Work;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import persistence.loader.tabDataSet.RowIdNameDescription;
import trestview.table.tablemodel.abstracttablemodel.ColumnsOrderMap;
import trestview.table.tablemodel.abstracttablemodel.ParametersColumn;
import trestview.table.tablemodel.abstracttablemodel.ParametersColumnMap;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class TableBaseModel<cL> extends ObservableDS {

    protected ArrayList<cL> tab;
    protected cL selectRow;
    protected RowIdNameDescription parentselectRow;
    protected ArrayList<ParametersColumn>  parametersOfColumns;
    protected Class tClass;
    protected RowIdNameDescription r;

    public TableBaseModel() {
    }
    public TableBaseModel (ObservableDS observableDS, Rule rule)  {
       super( observableDS,rule);
    }

    public ArrayList<ParametersColumn> getParametersOfColumns() {
        return parametersOfColumns;
    }

    public ArrayList<ParametersColumn> buildParametersColumn() {
        parametersOfColumns = new ArrayList<>();
        ColumnsOrderMap.getColumns(rule).stream().map(s-> parametersOfColumns.add(ParametersColumnMap.getParametersColumn(s))).count();
        return parametersOfColumns;
    }

    public ArrayList<cL> getTab() {
        return tab;
    }

    public void setTab(ArrayList<cL> tab) {
        this.tab = tab;
    }

    public cL getSelectRow() {return selectRow;   }

    public void setSelectRow(cL selectRow) {  this.selectRow = selectRow;

    }

    public Class gettClass() { return tClass;  }

    public RowIdNameDescription getParentselectRow() {
        return parentselectRow;
    }

    public void setParentselectRow(RowIdNameDescription parentselectRow) {
        this.parentselectRow = parentselectRow;
    }
}


