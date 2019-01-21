package persistence.loader.tabDataSet;

/**
 * �������� �������� ������ �������: ��������� � ���������
 */
public class RowSetting  extends RowIdNameDescription
{
	
	public String getSystemPath()
	{
		return systemPath;
	}

	public void setSystemPath(String systemPath)
	{
		this.systemPath = systemPath;
	}

	/**
	 * �������������� ������ �������: ��������� � ���������
	 * @param id			Id ���������
	 * @param name			��� ���������
	 * @param systemPath		���� � �� (� ������ xml) 
	 * @param description	�������� �������� ���������
	 */	
	public RowSetting(int id, String name, String scheme, String description) {	super(id, name, description); this.systemPath=systemPath; }
	public RowSetting() {	}	

	/** ���� � �� (� ������ xml)					*/ 		
	private String systemPath;
	
	
}
