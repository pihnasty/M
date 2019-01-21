/**
 * 
 */
package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowOrder;

import java.util.ArrayList;
import java.util.Date;
public class Order extends RowOrder
{
	public ArrayList<Line> getLines()				{	return lines;			}
	public void setLines(ArrayList<Line> lines)		{	this.lines = lines;		}
	public Order(int id, String name, Date dateBegin, Date dateEnd, String description, ArrayList<Line> lines) {super(id, name, dateBegin, dateEnd, description); this.lines=lines; }
	public Order(){	super();}
	public Order ( DataSet dataSet) 	{	super( dataSet,  Order.class );	 }
	private ArrayList<Line> lines = new ArrayList<Line>();
}
