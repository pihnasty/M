package trestmodel;

import designpatterns.ObservableDS;
import entityProduction.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import persistence.loader.DataSet;
import persistence.loader.SectionDataSet;
import persistence.loader.tabDataSet.RowTrest;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

/**
 * Created by Max on 19.02.2016.
 */
public class TrestModel extends ObservableDS {

    private DataSet dataSet;

    private SectionDataSet sectionDataSet;

    private Trest trest;
    private List<Trest> trests = new ArrayList();

    private ObjectProperty<Trest>  trestObjectProperty  = new SimpleObjectProperty<>();
    private ObjectProperty<DataSet> dataObjectProperty  = new SimpleObjectProperty<>();

    public Locale locale;

    public TrestModel(ObservableDS observableDS, Rule rule) {
        setLocale("ru"); //   ru en
        this.dataSet = new DataSet();

        this.sectionDataSet = new SectionDataSet(dataSet);

        for (RowTrest r: this.dataSet.getTabTrests()) trests.add( dataSet.createObject(r));

        for (Trest t: trests)  if (t.getId()==1)    this.trest= t;

        intersectionMachine();  // The intersection of the collection from the collection on line

    }

    /**
     * The intersection of the collection from the collection on line
     */
    private void intersectionMachine() {
        List<Typemachine> tpS = sectionDataSet.getTypemachines();
        List<Machine> forDelete = new ArrayList();
        for (int iType = 0; iType < tpS.size(); iType++) {
            List<Modelmachine> mmS = tpS.get(iType).getModelmachines();
            for (int iModel = 0; iModel < mmS.size(); iModel++) {
                List<Machine> mS = mmS.get(iModel).getMachines();
                for (int iMachine = 0; iMachine < mS.size(); iMachine++) {
                    Machine machineType = mS.get(iMachine);
                    boolean isTrestIsTipe = false;
                    for (Trest trest : trests)
                        for (Work work : trest.getWorks())
                            for (Machine machineTrest : work.getMachines()) {
                                if (machineType.getId() == machineTrest.getId()) {
                                    isTrestIsTipe = true;
                                    Machine m = machineTrest;
                                    m.setModelmachine(machineType.getModelmachine());
                                    mS.set(iMachine, m);
                                }
                            }
                    if (!isTrestIsTipe) forDelete.add(machineType);
                }
            }
        }
        for(int i=0; i<dataSet.getTabMachines().size(); i++ ) for (Machine machine: forDelete) if (machine.getId()== dataSet.getTabMachines().get(i).getId()) {
            dataSet.getTabMachines().remove(dataSet.getTabMachines().get(i));
        }
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {

        this.dataSet = dataSet;
    }

    public Trest getTrest() {
        return trest;
    }

    public void setTrest(Trest trest) {
        this.trest = trest;
    }

    public SectionDataSet getSectionDataSet() {
        return sectionDataSet;
    }

    public void setSectionDataSet(SectionDataSet sectionDataSet) {
        this.sectionDataSet = sectionDataSet;
    }


    /**Set the language for the application
     * @param s Language of the application ( new Locale(s))
     */
    private void setLocale(String s) {
        locale = Locale.getDefault();
        if (s != "") {
            locale = new Locale(s);
            Locale.setDefault(locale);
        }
    }

}
