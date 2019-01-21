package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

/**
 * �������� �������� ������ �������: �����
 * @author ���
 */
public class RowTrest extends RowIdNameDescription
{	
	/**
	 * �������������� ������ �����
	 * @param id	Id ������
	 * @param name	��� ������
	 * @param description	�������� ������
	 */
	public RowTrest(int id, String name, String description) {	super(id, name, description); }
	
	public RowTrest (DataSet dataSet, Class cL) {super( dataSet,  cL);}
	
	public RowTrest() {}	
}
