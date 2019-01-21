package persistence.loader.tabDataSet;
public class RowIdId2	{
	private int id;
	private int id2;
	private String description;

	public RowIdId2(Integer id, int id2, String description) 	{	this.id=id; this.id2=id2;	this.description=description;	}
	public RowIdId2()    {}
	public int getId()								{	return id;						}
	public void setId(int id)						{	this.id = id;					}
	public int getId2()								{	return id2;						}
	public void setId2(int id2)						{	this.id2 = id2;					}
	public String getDescription()					{	return description;				}
	public void setDescription(String description)	{	this.description = description;	}

	public boolean equals(RowIdId2 obj)	{
		if(obj == this) 	return true;
		if(obj == null)  	return false;
		if(!(getClass() == obj.getClass())) 	return false;
        if( (obj.getId() == this.getId()) && (obj.getId2() == this.getId2()) ) 		return true;
			else return false;
	}
}
