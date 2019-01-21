package persistence.loader.tabDataSet;

/**
 * �������� �������� ������ �������: "������ ������������"  (������ ������������ �� ���������� ������������ ��� ����� � ����������)
 * @author POM
 */

import persistence.loader.DataSet;

public class RowLinespec extends RowIdNameDescription
{
	/**
	 * �������������� ������:  "������ ������������"
	 * @param id			Id "������ ������������"
	 * @param name			��� "������ ������������". 
	 * @param m				������� �������� ����������� ������� � ���� ���������� ��������������� ��������	 (�������������� ��������)			
	 * @param sigma			������������������ ���������� ����������� ������� � ���� ���������� ��������������� ��������	 			
	 * @param description	�������� "������ ������"
	 */	
	public RowLinespec(int id, String name, double m,  double sigma, String description)		{	
		super(id, name, description);	setM(m);	setSigma(sigma);		
	}
	public RowLinespec()											{ 	super();	 					}
	public RowLinespec (DataSet dataSet, Class cL) 				{	super( dataSet,  cL);			}
	
	
	
	public double getM()											{	return m;						}
	public void setM(double m)										{	this.m = m;						}
	public double getSigma()										{	return sigma;					}
	public void setSigma(double sigma)								{	this.sigma = sigma;				}

	/** ������� �������� ����������� ������� � ���� ���������� ��������������� ��������	 (�������������� ��������)					*/ 		
	private double m=1;
	/** ������������������ ���������� ����������� ������� � ���� ���������� ��������������� ��������	 							*/ 	
	private double sigma=1;

	
	
}
