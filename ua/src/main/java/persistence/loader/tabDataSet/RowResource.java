package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

/**
 * �������� �������� ������ �������: ��������������� ������
 * @author POM
 */
public class RowResource extends RowIdNameDescription {
/**
 * �������������� ������: ��������������� ������
 * @param id			Id ���������������� �������
 * @param name			��� ���������������� �������
 * @param description	�������� ���������������� �������
 */
	public  RowResource(int id, String name, String description) 	{	super(id, name,  description); 		}
	public  RowResource() {}
	public  RowResource(DataSet dataSet, Class cL)					{	super(dataSet,  cL);				}
}