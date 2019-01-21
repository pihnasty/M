package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

public class RowFunctionOEM extends RowIdNameDescription {
	public RowFunctionOEM(int id, String name, String pathFileFR, String description)	{	super(id, name, description); 	this.setPathFileFR(pathFileFR);	}
	public RowFunctionOEM()																{ 	super(); 													}
	public RowFunctionOEM (DataSet dataSet, Class cL) 									{	super( dataSet,  cL);										}
	public String getPathFileFR()													    {   return pathFileFR;											}
	public void setPathFileFR(String pathFileFR)									    {   this.pathFileFR = pathFileFR;							    }
	private String pathFileFR="Path to File";	
}
