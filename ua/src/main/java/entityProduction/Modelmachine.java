/**
 *
 */
package entityProduction;



import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowModelmachine;

import java.util.ArrayList;

public class Modelmachine extends RowModelmachine {

    private ArrayList<Machine> machines;

      public Modelmachine(int id, String name, String img, ArrayList<Machine> machines, double overallDimensionX, double overallDimensionY, double workSizeX, double workSizeY, String description) {
        super(id, name, img, overallDimensionX, overallDimensionY, workSizeX, workSizeY, description);
        this.machines = machines;
        for (int i=0; i<this.machines.size(); i++)  machines.get(i).setModelmachine(this);
    }

    public Modelmachine(DataSet dataSet) {
        super(dataSet, Modelmachine.class);
    }

    public ArrayList<Machine> getMachines()             {      return machines;         }
    public void setMachines(ArrayList<Machine> machines){  this.machines = machines;    }
    

}
