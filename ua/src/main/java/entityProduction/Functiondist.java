package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowFunctiondist;

import java.util.ArrayList;

public class Functiondist extends RowFunctiondist {
    private double averageValue;
    private double meanSquareDeviation;
    private String pathData;

public Functiondist(int id, String name, ArrayList<Parametrfunctiondist> parametrfunctiondist, String description)	{
    super(id, name, description);
    this.averageValue = parametrfunctiondist.get(0).getAverageValue();
    this.meanSquareDeviation = parametrfunctiondist.get(0).getMeanSquareDeviation();
    this.pathData = parametrfunctiondist.get(0).getPathData();
}
public Functiondist()											{	super();								}
public Functiondist(DataSet dataSet)							{	super(dataSet, Operation.class);		}

    public double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(double averageValue) {
        this.averageValue = averageValue;
    }

    public double getMeanSquareDeviation() {
        return meanSquareDeviation;
    }

    public void setMeanSquareDeviation(double meanSquareDeviation) {
        this.meanSquareDeviation = meanSquareDeviation;
    }

    public String getPathData() {   return pathData;   }

    public void setPathData(String pathData) {   this.pathData = pathData;   }
}