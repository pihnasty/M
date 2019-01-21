package entityProduction;

import designpatterns.ColumnModelmachine;
import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowMachine;

public class Machine extends RowMachine implements ColumnModelmachine {

    private Modelmachine modelmachine;

    private Work work;
            
    public Machine() {
        super();
    }

    public Machine(int id, String name, double locationX, double locationY, double angle, double state, String description) {
        super(id, name, locationX, locationY, angle, state, description);
    }

    public Machine(DataSet dataSet) {
        super(dataSet, Machine.class);
    }

    public Modelmachine getModelmachine() {
        return modelmachine;
    }

    public void setModelmachine(Modelmachine modelmachine) {
        this.modelmachine = modelmachine;
    }

    public Work getWork() {  return work;  }

    public void setWork(Work work) {  this.work = work; }

}
