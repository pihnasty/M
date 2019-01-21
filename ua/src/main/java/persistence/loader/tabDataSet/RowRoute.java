package persistence.loader.tabDataSet;
/**
 * �������� �������� ������ �������: ��������������� �������
 * @author ���
 */

import persistence.loader.DataSet;

public class RowRoute extends RowIdNameDescription {
	/**
	 * �������������� ������ ��������������� �������
	 * @param id	Id ���������������� ��������
	 * @param name	�������� ���������������� ��������
	 * @param description	�������� ���������������� ��������
	 */
	public RowRoute(int id, String name, String description)	{	super(id, name, description); 	}	
	public RowRoute()	{}	
	public RowRoute(DataSet dataSet, Class cL)					{	super(dataSet, cL);				}
}
