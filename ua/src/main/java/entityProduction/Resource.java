package entityProduction;
import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowResource;

public class Resource extends RowResource {
	/**
	 * �������������� ������ ����� "��������������� ������"
	 * @param id			Id ��� "��������������� ������"
	 * @param name			�������� ���������������� �������
	 * @param description	�������� ������  "��������������� ������"
	 */
	public Resource(int id, String name, String description)									{	super(id, name,  description);								}
	public Resource()																			{																}
	public Resource(DataSet dataSet)															{	super(dataSet, Operation.class);								}
}