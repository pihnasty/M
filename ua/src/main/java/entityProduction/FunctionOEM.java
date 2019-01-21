package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowFunctionOEM;

public class FunctionOEM extends RowFunctionOEM	{
public FunctionOEM(int id, String name, String pathFileFR, String description)				{	super(id, name, pathFileFR, description);			}
public FunctionOEM()																		{														}
public FunctionOEM(DataSet dataSet)															{	super(dataSet, Operation.class);				    }
}