/**
 * 
 */
package persistence.loader;

import entityProduction.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.w3c.dom.*;
import persistence.loader._util._Date;
import persistence.loader.tabDataSet.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author ПОМ
 *
9 */
public class XmlRW	 
{
	private static DocumentBuilder builder;
    static Document doc;
/*___________________________________________________________________________________________________________________*/
	/**
	 * Загрузочный конфигурационный метод.
	 * @return Путь к файлу загрузки начальной информации из XML-файлов
	 * @throws Exception
	 */
	static public String readPathData() throws Exception 
	{
		String pathData="Путь к ";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();     		
 		File f = new File("Config/config.xml");	
        doc = builder.parse(f);
        Element root = doc.getDocumentElement();
        NodeList children = root.getChildNodes(); 
        for(int i =0; i< children.getLength(); i++ )
        {
        	Node child =  children.item(i);
        	if (child instanceof Element )
        	{
        		Element elementChild = (Element) child;	      	  
        		NamedNodeMap atr = elementChild.getAttributes();		 
        		for(int j =0; j< atr.getLength(); j++ ) 
        		{
        			Node atrNode = atr.item(j);       			
	        		if( atrNode.getNodeName().equals("Path")) pathData=atrNode.getNodeValue();	
	        	}	
        	}
        }	
		return pathData;
	}

	/**
	 * This will select  the directory where we will create a database
	 * @return	The directory path to the database
	 */
    static public String getPathData()
    {
		String pathData="";
		FXMLLoader loader = new FXMLLoader();
		loader.setResources(ResourceBundle.getBundle("ui"));

		DirectoryChooser chooser=new DirectoryChooser();
		chooser.setInitialDirectory(new File( "D:\\A\\M\\MyProjects"  )); // we set the current directory, which is defined in the config.xml
		chooser.setTitle(loader.getResources().getString("Select_db_download"));
		chooser.setInitialDirectory(new File( "D:\\A\\M\\MyProjects") );  //  .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File file=chooser.showDialog(new Stage());
		if (file != null) {
			chooser.setInitialDirectory(file);
			try {
				pathData=chooser.getInitialDirectory().getCanonicalPath()+"\\";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}



//		for (Field fd : DataSet.class.getDeclaredFields() )		{					// We go through all the fields of  DataSet.class
//			if ( fd.getName().substring(0,3).equals("tab"))	{	  	 				// and find those fields that begin with a tab......
//				if ( !new File(pathData+fd.getName()+".xml").exists()) return "";	// DataSet.tSettings.get(0).getSystemPath();
//				// and check whether there is a directory of data files (their name is the same as the field names) fd.getName()
//				// If at least one of the data files does not match or is not found, it returns the old path to the directory database
//			}
//		}

		return pathData;
    }

	/**
	 * Выбираем каталог, откуда будем записать данные БД
	 * @return	выбранная через showDialog(...) директория с базой данных
	 */
    static public String getSavePath(String s)
    {
    	String pathData="";
		FXMLLoader loader = new FXMLLoader();
		loader.setResources(ResourceBundle.getBundle("resources.ui"));
		DirectoryChooser chooser=new  DirectoryChooser();
		chooser.setInitialDirectory(new File( DataSet.tSettings.get(0).getSystemPath()  )); // we set the current directory, which is defined in the config.xml
		chooser.setTitle(loader.getResources().getString("Select_db_save"));
		File file = chooser.showDialog(new Stage());
		if (file != null) pathData = file.getAbsolutePath()+"\\";
  		return pathData;
    }


	/**
	 * Записывает данные в файл с указанным путем pathDataFile
	 * @param pathDataFile	Путь к имени файла
	 * @param tab			Коллекция ArrayList<cL>, которая записывается в файл
	 * @param TagNames		Имя тега корневого элемента
	 * @param cL			Тип данных элементов, хранящихся в коллекции ArrayList<cL>
	 * @return
	 * @throws ParserConfigurationException
	 * @throws Throwable
	 */
	static public void writeTab(String pathDataFile, Object tab, String TagNames, Class cL ) throws ParserConfigurationException, Throwable
	   {
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	     builder = factory.newDocumentBuilder();
	     doc = builder.newDocument();
		 File f = new File(pathDataFile+".xml");
		 Transformer t = TransformerFactory.newInstance().newTransformer();
		 writeTabClass (tab, cL, TagNames);
	     t.setOutputProperty(OutputKeys.INDENT,"yes");
	     t.transform(new DOMSource(doc),new StreamResult(new FileOutputStream(f)));
	   	}

	/**
	 * Проверяет, соответствует ли элементы коллекции заданному типу и в случае соответствия формирует xml-файл
	 * @param tab
	 * @param cL
	 * @param TagNames
	 * @throws NoSuchFieldException
	 * @throws DOMException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	static public  <cL> void writeTabClass (Object tab, Class cL , String TagNames) throws NoSuchFieldException, DOMException, IllegalArgumentException, IllegalAccessException
	{
		     if ( tab !=null )
	    	     if ((( ArrayList<Object> )tab).isEmpty() == false  )
	    	     {
	    				Element root = doc.createElement( TagNames );
	    				doc.appendChild(root);
	    				for ( cL r : (ArrayList<cL> )tab)
	    				{
	    					Element child = doc.createElement(cL.getSimpleName());
	    					root.appendChild(child);
	    					for(Field field :  fieldsCl(cL))
	    					{
	    						field.setAccessible(true);
	    						if ( field.getType() == Date.class )		child.setAttribute(field.getName(),   _Date.toStringForDate((Date)field.get(r))  );
	    															else	child.setAttribute(field.getName(),  (field.get(r)).toString() );
	    						field.setAccessible(false);
	    					}
	    				}
	    	     }
	}
	/**
	 * Формируе и возвращаем таблицу, которая наследовалась от RowIdId2 или RowIdNameDescription (таблицу сущности или реестра)
	 * Используя путь к файлу, считываем информацию в ArrayList<cL> множество строк с именем тега cL.
	 * @param pathData	Путь к файлу, где  база данных в виде XML-файлов
	 * @param cL		Имя строки таблицы. Соответствует типу данных элементов, которые хранятся в ArrayList (Пример: cL=RowTrest для ArrayList<RowTrest> ).
	 * @return tab		Содержит коллекцию строк таблицы (каждый элемент коллекции является строкой)
	 * @throws Throwable
	 */
	static public  <cL> Object readTab(String pathData, Class cL) throws Throwable
	   {
	    File f = new File(pathData+".xml");
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        Document doc = builder.parse(f);
        Element root = doc.getDocumentElement();
        NodeList children = root.getChildNodes();   		// считываем узлы корневого элемента для заданного файла *.xml

		ArrayList<cL> tab= new ArrayList<cL>();  			// инициализируем таблицу, которая будет содержать строки (объекты класса cL )
	//	Field [] fields =fieldsCl(cL);						// получаем все поля для класса cL
	    for(int i =0; i< children.getLength(); i++ )		// проходимся по всем узлам корневого элемента
	    {
	    	Node child =  children.item(i);					// промежуточный этап. Для простоты понимания вводим отдельный узел, куда передаем ссылку на узел children.item(i)
	    	if (child instanceof Element ) 					// проверяем узел на элемент
	    	{
    		Element elementChild = (Element) child;			// приводим в соответствие тип данных  (приводим узел к элементу)
	    	String s = elementChild.getTagName();			// получаем имя тега элемента
	    	if(s.equals(cL.getSimpleName()))				// полученное имя тега проверяем на совпадение с именем поля класса cL, характеризующего строку.
	    	{
	    		cL row = (cL)Class.forName(cL.getName()).newInstance();   		// создаем новый объект с типом данных cL для записи в его поля значения атрибутов строки
        	    for (Field fd : fieldsCl(cL)) 									// проходимся по всем полям объекта класса cL
        	    {
        	    	NamedNodeMap atr = elementChild.getAttributes();			// получаем все атрибуты строки элемента, которые соответствует строке таблицы
    				fd.setAccessible(true);
        	    	for(int j =0; j< atr.getLength(); j++ )						// перебираем все атрибуты и смотрим , где совпадения имен атрибутов и имен полей класса cL в объекте row
        	    	{
        	    		Node atrNode = atr.item(j);
        	    		if( atrNode.getNodeName().equals(fd.getName()) ) 		// если совпадения имен атрибутов и имен полей класса cL в объекте row, то значение атрибута присваеваем значению поля в объекте row
        	    		{	fd.set(row,	parseValue (fd, atrNode.getNodeValue(), "XmlRW"));	}
        	    	}
    				fd.setAccessible(false);
        	    }
    	        tab.add(row); 													// по мере формирования объекта row (строки таблицы) заносим ее построчно в таблицу
	    	}
	    	else System.out.println("Некорректный тег. Должен быть: "+cL.getSimpleName()+", а у Вас: "+s);
	    	}
	    }
	    return tab;										//	возвращаем готовую таблицу Машин, Сотрудников
	  }

	/**
	 * Часто мы имеем дело с полями, в которые необходимо записывать значение с помощью рефлексии. Эти проблемы и решает данный метод. Получает в качестве параметра объект (Field fd), выясняется тип данных, который в нем хранитсяю
	 * В зависимости от данного типа данных значение (Object o), которое мы собираемся записать в ячейку/поле, приводится к типу данных fd.getType()
	 * @param fd	поле, куда мы собираемся сохранить значение
	 * @param o		значение, которое необходимо записать в поле
	 * @param flag	дополнительный параметр, который учитывает тип записи в зависимости от того, в каком классе мы собираемся использовать метод
	 * @return		приведенное значение (Object o) в зависимости от поля, в котором оно хранится
	 */
	static public Object parseValue (Field fd, Object o, String flag)
	{
		 if (fd.getType() == int.class ) 	return 	(int) Integer.parseInt( 		o.toString());
		 if (fd.getType() == double.class ) return	(double) Double.parseDouble(o.toString());
		 if (fd.getType() == String.class )	return	o.toString();
		 if (fd.getType() == Date.class && flag=="XmlRW")				return  _Date.toDateForString   ((String)o);							//  if (fd.getType() == Date.class )   fd.set(row,      	 _Date.toDateForString (atrNode.getNodeValue()));
		 if (fd.getType() == Date.class && flag=="PModelTable")			return  _Date._toDateForString	((String)o	, Locale.US);			// Специфический формат, используемый в PModelTable
		 return o;
	}

    /**
     * Возращаем поля тестируемого класса
     * @param cL	Тестируемый класс
     * @return
     */
    static public <cL> Field [] fieldsCl(Class cL)
    {
    	Field [] cLfields = cL.getDeclaredFields();							// получаем поля класса
		Class cLsuper = cL.getSuperclass();

		Field [] superfields = cLsuper.getDeclaredFields();					// получаем поля суперкласса
		Class cLsuper2 = cL.getSuperclass().getSuperclass();
		Field [] super2fields = cLsuper2.getDeclaredFields();				// получаем поля СуперСуперКласса


		Field [] fields = new Field [super2fields.length+superfields.length+cLfields.length];
		System.arraycopy(super2fields, 0, fields, 0, 										super2fields.length);
		System.arraycopy(superfields,  0, fields, super2fields.length, 						superfields.length);
		System.arraycopy(cLfields,     0, fields, super2fields.length+superfields.length,   cLfields.length);	  // получаем массив, содержащий поля класса, суперкласса и СуперСуперКласса
		return fields;
    }

    /**
     * Метод записывает поля объектов Work (Object o) -> RowWork (Object ro). Служит для заполнения полей от объектов Trest в объекты DataSet ( из элемента Work коллекции Trest.works  в элемент RowWork коллекции DataSet.tabWorks)
     * @param ro	Объект типа Work
     * @param o	Объект типа RowWork
     */
    static public  void FieldToField(Object ro, Object o)
    {
	for (Field fd : XmlRW.fieldsCl(ro.getClass()))
        try
        {	fd.setAccessible(true);
            fd.set(ro, fd.get(o));
			fd.setAccessible(false);
        } catch (IllegalArgumentException | IllegalAccessException exp)   {  exp.printStackTrace(); }
	}

    /**
     * Делаем изменения в DataSet ds в соответствие с данными объекта Object o. При изменение Object o, которым является  o.getClass()==Trest.class или o.getClass()==Work.class или o.getClass()==Machine.class ...
     * изменяется информация, которая касается его в DataSet ds
     * @param ds	база DataSet ds
     * @param o		Объект типа  o.getClass()==Trest.class или o.getClass()==Work.class или o.getClass()==Machine.class ...
     */
    static public  void FieldToField_ifClass(DataSet ds, Object o)
    {   
    	if ( o.getClass()== Employee.class)		// Если выделенный узел Employee.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabEmployees)
     	   for (RowEmployee r: ds.getTabEmployees())if (((Employee)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== FunctionOEM.class)		// Если выделенный узел Employee.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabEmployees)
     	   for (RowFunctionOEM r: ds.getTabFunctionOEMs())if (((FunctionOEM)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Line.class)		// Если выделенный узел Order.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabOrders)
    		for (RowLine r: ds.getTabLines())		if (((Line)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Lineroute.class)		// Если выделенный узел Order.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabOrders)
    		for (RowLineroute r: ds.getTabLineroutes())		if (((Lineroute)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Linespec.class)		// Если выделенный узел Order.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabOrders)
    		for (RowLinespec r: ds.getTabLinespecs())		if (((Linespec)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Machine.class)		// Если выделенный узел Machine.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabMachines)
    		for (RowMachine r: ds.getTabMachines())	if (((Machine)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 		// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Operation.class)		// Если выделенный узел Machine.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabMachines)
    		for (RowOperation r: ds.getTabOperations())	if (((Operation)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 		// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Order.class)		// Если выделенный узел Order.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabOrders)
    		for (RowOrder r: ds.getTabOrders())		if (((Order)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Resource.class)		// Если выделенный узел Order.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabOrders)
    		for (RowResource r: ds.getTabResources())		if (((Resource)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Route.class)		// Если выделенный узел Order.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabOrders)
    		for (RowRoute r: ds.getTabRoutes())		if (((Route)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Subject_labour.class)		// Если выделенный узел Order.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabOrders)
    		for (RowSubject_labour r: ds.getTabSubject_labours())		if (((Subject_labour)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Trest.class)			// Если выделенный узел Trest.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabTrests)
    		for (RowTrest r: ds.getTabTrests())		if (((Trest)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 			// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Unit.class)			// Если выделенный узел Trest.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabTrests)
    		for (RowUnit r: ds.getTabUnits())		if (((Unit)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 			// выбираем нужную строку таблицы для изменения. Изменяем.
    	if ( o.getClass()== Work.class)			// Если выделенный узел Work.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabWorks)
    		for (RowWork r: ds.getTabWorks())		if (((Work)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 				// выбираем нужную строку таблицы для изменения. Изменяем.

    

   
    	if ( o.getClass()== Line.class)		// Если выделенный узел Order.class, то приводим выделенный объект, хранящийся в узле, к нужному типу и записываем изменения в 	DataSet для соответствующей таблице (tabOrders)
    		for (RowLine r: ds.getTabLines())		if (((Line)o).getId()==r.getId()) XmlRW.FieldToField(r, o); 	 	// выбираем нужную строку таблицы для изменения. Изменяем.
     }

	/**
	 * Resource loader FXML
	 * @param view
	 * @param controller
	 * @param getResourceFxml
	 * @param getBundleResources
     * @param getBundleCss
     */
	public static FXMLLoader fxmlLoad (Object view, Object controller, String getResourceFxml, String getBundleResources, String getBundleCss)  {
		FXMLLoader fxmlLoader = null;
		if (getResourceFxml!="") {
			fxmlLoader = new FXMLLoader(view.getClass().getResource("/"+getResourceFxml));
			if (getBundleResources!="") fxmlLoader.setResources(ResourceBundle.getBundle(getBundleResources));
			if (getBundleCss!="") ((Parent)view).getStylesheets().add(getBundleCss);
			fxmlLoader.setRoot(view);
			if (controller!=null) fxmlLoader.setController(controller);        // or  fx:controller="ui.rootPane.menu.TMenuController"
			// or <fx:root type="trestview.menu.TMenuView" xmlns:fx="http://javafx.com/fxml"  fx:controller="trestview.menu.TMenuController" >
			try { fxmlLoader.load();  } catch (IOException exception) {  throw new RuntimeException(exception);       }
		}
		return fxmlLoader;
	}

	/**
	 * @param tab			коллекция объектов, один из которых подлежат удалению
	 * @param Rowtab		таблица (коллекция) строк в DataSet с уникальным id2, в которых хранятся данные для формирования объектов
	 * @param RowIdIdtab    таблица (коллекция) реестров строк в DataSet с уникальным id и id2, в которых хранятся данные для формирования объектов
	 */
	public static  <cL, RowcL, RowIdIdcL> void delRow (cL o, ArrayList <cL> tab, ArrayList <RowcL> Rowtab, ArrayList <RowIdIdcL> RowIdIdtab){
		int id = ((RowIdNameDescription)o).getId();
		tab.remove(o);
		for ( RowcL r : Rowtab ) 			if ( id== ((RowIdNameDescription) r).getId() )  {	Rowtab.remove(r);		break;	}	// удаляем строку данных об объекте
		for ( RowIdIdcL wr : RowIdIdtab )	if ( id == ((RowIdId2) wr).getId2() )  			{	RowIdIdtab.remove(wr);	break;	}	// удаляем строку  связи объекта с родителем																								   {	RowIdIdtab.remove(wr);	break;	}		// удаляем строку реестра с данными об связи объектах
	}

}


