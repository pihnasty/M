package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowLinespec;

import java.util.ArrayList;

public class Linespec extends RowLinespec
{
	private Resource resource;
	private FunctionOEM functionOEM;
	private Unit unit;

	/** Единица измерения предмет труда (правильнее продукт) в строке получен. заказа */
	private String unitName 		= "ед.изм";
	/** Функция распределения использования технологических ресурсов для  выполнения технологической операции	 					*/
	/**	Каждая технологическая операция обрабатывает предмет труда, потребление ресурса которого распределено по заданному закону	*/
	private String functionOEMNama 	= "Закон распределения";
	/** Имя использованног технологических ресурсов для  выполнения технологической операции	 									*/
	private String resourceName	= "resourceName";

	/**
	 * Инициализирует объект:  "строка спецификации"
	 * @param id			Id "строка спецификации"
	 * @param name			Имя "строка спецификации".
	 * @param resource	Название (Name) используемого ресурса
	 * @param m				Среднее значения потребления ресурса в ходе выполнения технологической операции	 (математическое ожидание)
	 * @param sigma			Среднеквадратичное отклонение потребления ресурса в ходе выполнения технологической операции
	 * @param functionOEM		Функция распределения использования технологических ресурсов (Name) для  выполнения технологической операции. 	Каждая технологическая операция обрабатывает предмет труда, потребление ресурса которого распределено по заданному закону
	 * @param unit			Среднеквадратичное отклонение потребления ресурса в ходе выполнения технологической операции
	 * @param description	Описание "строка заказа"
	 */
	public Linespec(int id, String name, Resource resource , double m, double sigma, FunctionOEM functionOEM, Unit unit, String description)	{
		super(id, name, m,  sigma, description);
		this.unitName = unit.getName();
		this.functionOEMNama = functionOEM.getName();
		this.resourceName = resource.getName();
	}
	public Linespec()	{	}
	public Linespec(DataSet dataSet)							{	super(dataSet, Linespec.class);			}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public FunctionOEM getFunctionOEM() {
		return functionOEM;
	}

	public void setFunctionOEM(FunctionOEM functionOEM) {
		this.functionOEM = functionOEM;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getFunctionOEMName() {
		return functionOEMNama;
	}

	public void setFunctionOEMName(String functionOEMNama) {
		this.functionOEMNama = functionOEMNama;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}
