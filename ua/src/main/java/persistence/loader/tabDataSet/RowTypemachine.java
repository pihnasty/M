package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

public class RowTypemachine extends RowIdNameDescription {

    public RowTypemachine(int id, String name, String description) {
        super(id, name, description);
    }

    public RowTypemachine() {
        super();
        this.setName("Пресса кривошипные");
    }

    public RowTypemachine(DataSet dataSet, Class cL) {
        super(dataSet, cL);
        this.setName("Пресса кривошипные");
    }
}
