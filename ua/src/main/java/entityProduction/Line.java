package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowLine;

import java.util.ArrayList;
import java.util.Date;
public class Line extends RowLine	{
	public Line(int id, String name, Subject_labour subject_labour, Unit unit, double quantity, Date dateBegin, Date dateEnd, String description){
			super(id, name, quantity, dateBegin, dateEnd, description);
		    this.subject_labour = subject_labour;
    		subject_labourName 	= subject_labour.getName();
    		price 				= subject_labour.getPrice();
		    unitName            = unit.getName();
	}
	public Line()						   { 	super(); 					}
	public Line ( DataSet dataSet)			{	super( dataSet,  Line.class);		}


	public double getPrice()				{	return price;				}
	public void setPrice(double price)		{	this.price = price;			}


	public String getSubject_labourName() {
		return subject_labourName;
	}

	public void setSubject_labourName(String subject_labourName) {
		this.subject_labourName = subject_labourName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public ArrayList<Route> getRoutes() {
		return subject_labour.getRoutes();
	}

	private Subject_labour subject_labour;
	private String subject_labourName;
	private double price = 0.0;
	private String unitName = "piece";
}


