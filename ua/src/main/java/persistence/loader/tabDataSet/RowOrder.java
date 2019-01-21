/**
 * 
 */
package persistence.loader.tabDataSet;

import designpatterns.ColumnDate;
import persistence.loader.DataSet;

import java.util.Date;
public class RowOrder extends RowIdNameDescription implements ColumnDate {
	public RowOrder(int id, String name, Date dateBegin, Date dateEnd,	String description)
	{	super(id, name, description); 	this.dateBegin=dateBegin;	this.dateEnd=dateEnd;	}
	
	public RowOrder()							{ 	super(); 					}
	public RowOrder (DataSet dataSet, Class cL){	super( dataSet,  cL);		}
	
	
	public Date getDateBegin()					{	return dateBegin;			}
	public void setDateBegin(Date dateBegin)	{	this.dateBegin = dateBegin;	}
	public Date getDateEnd()					{	return dateEnd;				}
	public void setDateEnd(Date dateEnd)		{	this.dateEnd = dateEnd;		}

	private Date dateBegin 	= 	new Date();
	private Date dateEnd 	=	new Date();	
}
