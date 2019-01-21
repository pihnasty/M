package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

public class RowUnit extends RowIdNameDescription {

	public RowUnit(int id, String name, String description) {	super(id, name, description); 	}	
	public RowUnit (DataSet dataSet, Class cL) 			{	super( dataSet,  cL);			}
	public RowUnit() {}

}
