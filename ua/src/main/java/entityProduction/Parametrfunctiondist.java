package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowParametrfunctiondist;

public class Parametrfunctiondist extends RowParametrfunctiondist {

public Parametrfunctiondist(int id,String name,double averageValue,double meanSquareDeviation,String pathData,String description)
    {	super(id,name,averageValue,meanSquareDeviation,pathData,description);			}
public Parametrfunctiondist(DataSet dataSet)							{	super(dataSet, Parametrfunctiondist.class);		}
}


