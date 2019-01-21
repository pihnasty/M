/**
 * 
 */
package entityProduction;



import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowTrest;

import java.util.ArrayList;
public class Trest extends RowTrest {

	private ArrayList<Work>  	works;
	public Trest(int id, String name,ArrayList<Work> works, String description)	{
		super(id, name, description); this.works = works;
	}

	public Trest ( DataSet dataSet) 	{	super( dataSet,  Work.class );	}
	public Trest() {}

//	public void show () 	{	try { DataSet.showObjTabs(this, new Class [] {Work.class} , works);}	catch (Throwable exp)   {  exp.printStackTrace(); } 	}

	public ArrayList<Work> getWorks()			{	return works;		}
	public void setWorks(ArrayList<Work> works)	{	this.works = works;	}

}


