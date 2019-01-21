package persistence.loader.tabDataSet;

/**
 * Описание связи Производство-Машина (оборудование)
 * @author ПОМ
 *
 */
public class RowWorkMachine extends RowIdId2
{
	/**
	 * Инициализирует строку связи  связи Производство-Машина (оборудование)
	 * @param id	Id производства
	 * @param id2	Id машины
	 * @param description	Описание строки
	 */
	public RowWorkMachine(int id, int id2, String description)  {	super(id, id2, description);	}
	public RowWorkMachine(){}	
}
