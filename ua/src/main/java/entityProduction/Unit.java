package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowUnit;

public class Unit extends RowUnit {
	public Unit(int id, String name, String description)		{	super(id, name, description);	}
	public Unit()												{ 	super();	 					}	
	public Unit ( DataSet dataSet)								{	super( dataSet,  Unit.class);	}
}
