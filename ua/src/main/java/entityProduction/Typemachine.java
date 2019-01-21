/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityProduction;



import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowTypemachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Typemachine extends RowTypemachine {

    private ArrayList<Modelmachine> modelmachines;

    public Typemachine(int id, String name,ArrayList<Modelmachine> modelmachines, String description) {
        super(id, name, description);
        this.modelmachines = modelmachines;
    }

    public Typemachine(DataSet dataSet) {
        super(dataSet, Typemachine.class);
    }

    public ArrayList<Modelmachine> getModelmachines() {       return modelmachines;    }

    public void setModelmachines(ArrayList<Modelmachine> modelmachines) {       this.modelmachines = modelmachines;    }

}