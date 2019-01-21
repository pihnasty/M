/**
 * 
 */
package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

public class RowSubject_labour extends RowIdNameDescription
{	
	public double getPrice()			{		return price;		}
	public void setPrice(double price)	{		this.price = price;	}
	public RowSubject_labour(int id, String name, double price, String description) 	{	super(id, name, description);	 this.price= price;	}
	public RowSubject_labour()	{}
	public RowSubject_labour (DataSet dataSet, Class cL) {super( dataSet,  cL);}

	private	double price = 0.0;

}
