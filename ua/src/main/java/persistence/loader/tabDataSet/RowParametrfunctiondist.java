package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

public class RowParametrfunctiondist extends RowIdNameDescription {
	private double averageValue = 1.0;
	private double meanSquareDeviation =1.0;
	private String pathData;

	public RowParametrfunctiondist(int id,String name,double averageValue,double meanSquareDeviation, String pathData,String description){
		super(id, name, description);
		this.averageValue = averageValue; this.meanSquareDeviation = meanSquareDeviation;  this.pathData = pathData;
	}
	public RowParametrfunctiondist()										{ 	super(); 						}
	public RowParametrfunctiondist(DataSet dataSet, Class cL) 				{	super( dataSet,  cL);			}

	public double getMeanSquareDeviation() {
		return meanSquareDeviation;
	}

	public void setMeanSquareDeviation(double meanSquareDeviation) {
		this.meanSquareDeviation = meanSquareDeviation;
	}

	public double getAverageValue() {
		return averageValue;
	}

	public void setAverageValue(double averageValue) {
		this.averageValue = averageValue;
	}

	public String getPathData() {
		return pathData;
	}

	public void setPathData(String pathData) {
		this.pathData = pathData;
	}
}
