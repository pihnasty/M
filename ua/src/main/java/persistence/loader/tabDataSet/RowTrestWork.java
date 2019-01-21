package persistence.loader.tabDataSet;

/**
 * Описание связи Трест-Производство
 * @author ПОМ
 *
 */
public class RowTrestWork extends RowIdId2 {
    /**
     * @param id	Id треста
     * @param id2	Id производства
     */
	public RowTrestWork(int id, int id2, String description)  {	super(id, id2, description);	}
	public RowTrestWork()    {    }
	
}
