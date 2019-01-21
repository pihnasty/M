package trestview.table.tablemodel;

import designpatterns.ObservableDS;
import entityProduction.*;
import persistence.loader.DataSet;
import persistence.loader.SectionDataSet;
import persistence.loader.XmlRW;
import persistence.loader.tabDataSet.*;
import trestview.hboxpane.HboxpaneModel;
import trestview.hboxpane.MethodCall;
import trestview.resourcelink.ResourceLinkModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static persistence.loader.XmlRW.FieldToField_ifClass;

public class TableModel <cL> extends TableBaseModel implements Observer {

    private MethodCall methodCall;

    public TableModel(DataSet dataSet ) {
      //  super(null);  /* -------- */
        this.parametersOfColumns = buildParametersColumn() ;
        this.dataSet = dataSet;
    }


    /**
     * @param  rule    The data type for a table row. This is [RowWork.class] for the table = [ArrayList<RowWork>].
     */
    public TableModel(ObservableDS observableDS, Rule rule) {
        super(observableDS,rule);
        this.tClass =  rule.getClassTab();


        if(observableDS instanceof TableModel)  parentselectRow = (RowIdNameDescription) ((TableModel)observableDS).getSelectRow();

        switch (rule) {
            case Work:
                this.tab = trest.getWorks();
                break;
            case Machine:
                this.tab = trest.getWorks().get(0).getMachines();
                break;
            case Subject_labour:
                if(parentselectRow instanceof Work) tab = ((Work)(((TableModel) observableDS).tab.get(0))).getSubject_labours();
                break;
            case Route:
                if(parentselectRow instanceof Subject_labour) tab = ((Subject_labour)(((TableModel) observableDS).tab.get(0))).getRoutes();
                if(parentselectRow instanceof Line) tab = ((Line)(((TableModel) observableDS).tab.get(0))).getRoutes();
                break;
            case Lineroute:
                if(parentselectRow instanceof Route) tab = ((Route)(((TableModel) observableDS).tab.get(0))).getLineroutes();
                break;
            case Linespec:
                if(parentselectRow instanceof Lineroute) tab = ((Lineroute)(((TableModel) observableDS).tab.get(0))).getLinespecs();
                break;
            case Order:
                if(parentselectRow instanceof Work) tab = ((Work)(((TableModel) observableDS).tab.get(0))).getOrders();
                break;
            case Line:
                if(parentselectRow instanceof Order) tab = ((Order)(((TableModel) observableDS).tab.get(0))).getLines();
                break;
            case RowTypemachine:
            case RowWork:
            case RowFunctiondist:
            case RowUnit:
            case RowOperation:
                this.tab = dataSet.getTabIND(tClass);
            break;
        }
        this.selectRow = tab.get(0);
        this.parametersOfColumns = buildParametersColumn() ;
    }

    public TableModel(DataSet dataSet, Rule rule) {
        this.rule = rule;
        this.tClass =  rule.getClassTab();
        this.tab = dataSet.getTabIND(tClass);
        this.parametersOfColumns = buildParametersColumn() ;
        this.dataSet = dataSet;
    }

    public TableModel(ArrayList<cL> tab, Rule rule) {
        // TODO Найти метод определения базового типа данных [cL] в массиве ArrayList<cL>
        this.rule=rule;
        this.tab = tab;
        this.tClass = rule.getClassTab();
        //  this.tClass = RowFunctiondist.class;
        this.parametersOfColumns = buildParametersColumn() ;
        this.dataSet = null;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass() == HboxpaneModel.class)    { updateHBoxpaneModel((HboxpaneModel) o);  }
        if (o.getClass() == TableModel.class ) {
            tab = getNewTab((TableModel) o);
            parentselectRow = (RowIdNameDescription) ((TableModel) o).selectRow;
            selectRow = tab!=null&&!tab.isEmpty() ? tab.get(0) : null;
            methodCall = MethodCall.selectRowTable;
        }
        changed();
    }

    private ArrayList<cL>  getNewTab(TableModel o) {
        ArrayList tab = new ArrayList();
        if (o.selectRow == null) return tab;
        switch (o.getRule()) {
            case Work:
                if( rule == Rule.Machine)        tab = ((Work)(o.selectRow)).getMachines();
                if (rule == Rule.Subject_labour) tab = ((Work) (o.selectRow)).getSubject_labours();
                if (rule == Rule.Order)          tab = ((Work) (o.selectRow)).getOrders();
                break;
            case Subject_labour:
                if (rule == Rule.Route)           tab = ((Subject_labour) (o.selectRow)).getRoutes();
                break;
            case Route:
                if (rule == Rule.Lineroute)          tab = ((Route) (o.selectRow)).getLineroutes();
                break;
            case Lineroute:
                if (rule == Rule.Linespec)          tab = ((Lineroute) (o.selectRow)).getLinespecs();
                break;
            case Order:
                if (rule == Rule.Line)           tab = ((Order) (o.selectRow)).getLines();
                break;
            case Line:
                if (rule == Rule.Route)           tab = ((Line) (o.selectRow)).getRoutes();
                break;
        }
        return tab;
    }

    public void selectRowForTwoTableModel() {
        methodCall = MethodCall.selectRowTable;
        if (rule == Rule.Work) changed();
        if (rule == Rule.Subject_labour) changed();
        if (rule == Rule.Route) changed();
        if (rule == Rule.Lineroute) changed();
        if (rule == Rule.Order) changed();
        if (rule == Rule.Line) changed();
    }

    private void updateHBoxpaneModel(HboxpaneModel o) {
        switch (o.getMethodCall()) {
            case addRowTable:
                methodCall = MethodCall.addRowTable;
                addEntity();

                break;
            case saveRowTable:
                methodCall = MethodCall.saveRowTable;
                for(Object r: tab) FieldToField_ifClass(dataSet, r);
                this.dataSet.saveDataset();
                if (  rule == Rule.Machine)  {

                    for(Work w: trest.getWorks())
                        if ( w == parentselectRow  )  this.tab = w.getMachines();
                    changed();
                }
                break;
            case editRowTable:
                methodCall = MethodCall.editRowTable;
                break;
            case delRowTable:
                methodCall = MethodCall.delRowTable;
                if (tClass == RowTypemachine.class )    XmlRW.delRow (selectRow, tab, dataSet.getTabTypemachines(), dataSet.getTabModelmachineTypemachines());
                if (tClass == RowMachine.class )        XmlRW.delRow (selectRow, tab, dataSet.getTabMachines(), dataSet.getTabWorksMachines());
                if (tClass == RowWork.class )           XmlRW.delRow (selectRow, tab, dataSet.getTabWorks(), dataSet.getTabTrestsWorks());

                if (tClass == Work.class )              XmlRW.delRow (selectRow, tab, dataSet.getTabWorks(), dataSet.getTabTrestsWorks());
                if (tClass == Machine.class )           XmlRW.delRow (selectRow, tab, dataSet.getTabMachines(), dataSet.getTabWorksMachines());

                break;



            default:
                break;
        }
    }

    private void addEntity() {

            switch (rule) {
                case RowFunctiondist:
                    break;
                case Work:
                    createEntityRowentity();
                    dataSet.getTabTrestsWorks().add(new RowTrestWork(trest.getId(),r.getId(),""));
                    break;
                case Machine:
                    createEntityRowentity();
//                    dataset.getTabWorksMachines().add(new RowWorkMachine(parentselectRow.getId(),r.getId(),""));
//                    dataset.getTabModelmachineMachines().add( new RowModelmachineMachine(idModelMachine ,r.getId(),"")  );       // пробывал добавить оборудование
           //update(observableModel,null);
                    break;
                default:
                    createRowentity();
            }
    }

    private void createRowentity()  {
        try {
        Constructor constructor;
        constructor = tClass.getConstructor(DataSet.class, Class.class);
        selectRow = constructor.newInstance(this.dataSet,tClass);
        tab.add(selectRow);
        }   catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)     { e.printStackTrace(); }
    }

    private void createEntityRowentity()  {
        try {
            Constructor constructor = tClass.getSuperclass().getConstructor(DataSet.class, Class.class);
            r = (RowIdNameDescription) constructor.newInstance(this.dataSet, tClass.getSuperclass());

            selectRow = dataSet.createObject(r);


            if (tClass ==   Machine.class) {

                dataSet.addRowToDataSet(r, parentselectRow, dataSet.getTabModelmachines().get(0));
                for (Typemachine typemachine : sectionDataSet.getTypemachines())
                    for (Modelmachine modelmachine : typemachine.getModelmachines())
                        if (dataSet.getTabModelmachines().get(0).getId() == modelmachine.getId()) {
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                            ((Machine) selectRow).setModelmachine(modelmachine);
                        }

                ((Machine) selectRow).setWork((Work) parentselectRow);
            }


            tab.add(selectRow);
            List<RowIdNameDescription> tabRow = dataSet.getTabIND(tClass.getSuperclass());
            tabRow.add(r);

//changed();

        }   catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)     { e.printStackTrace(); }
    }

    public MethodCall getMethodCall() { return methodCall;  }


}


