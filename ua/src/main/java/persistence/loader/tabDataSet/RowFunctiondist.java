package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

public class RowFunctiondist extends RowIdNameDescription {
	public RowFunctiondist(int id, String name, String description)	{	super(id, name, description); 	}
	public RowFunctiondist()										{ 	super(); 						}
	public RowFunctiondist(DataSet dataSet, Class cL) 				{	super( dataSet,  cL);			}
}
