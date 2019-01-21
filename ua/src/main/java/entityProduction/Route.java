package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowRoute;

import java.util.ArrayList;

public class Route extends RowRoute	{
	public ArrayList<Lineroute>   getLineroutes()					{	return lineroutes;				 	}
	public void setLineroutes(ArrayList<Lineroute> lineroutes)		{	this.lineroutes =lineroutes;		}

	public Route(int id, String name, ArrayList<Lineroute> lineroutes, String description ) {	super(id, name,  description); this.lineroutes =lineroutes; }
	public Route(){	super();}
	public Route ( DataSet dataSet) 	{	super( dataSet,  Route.class );	 }
	private ArrayList<Lineroute> lineroutes = new ArrayList<Lineroute>();
}
