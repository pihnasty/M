package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

public class RowEmployee   extends RowIdNameDescription
{
	public RowEmployee()    {  super();  }
	public RowEmployee(int id, String name, String description)   	 {	super(id, name, description);   }
	public RowEmployee (DataSet dataSet, Class cL)					 {	super( dataSet,  cL);			}
}
