/**
 * 
 */
package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowSubject_labour;

import java.util.ArrayList;

public class Subject_labour extends RowSubject_labour {

	private String unitName;
	private ArrayList<Route> routes;

	public Subject_labour(int id, String name, double price, String unitName, ArrayList<Route> routes, String description) {
		super(id, name, price, description);
		this.routes = routes;
		this.unitName = unitName;
	}
	public Subject_labour ( DataSet dataSet) 																{	super( dataSet,  Subject_labour.class );	 					}
	public Subject_labour()	{}





	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public ArrayList<Route> getRoutes()							{		return routes;			}
	public void setRoutes(ArrayList<Route> routes)				{		this.routes = routes;	}

}
