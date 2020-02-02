/**
 * 
 */
package persistence.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.w3c.dom.Document;
import persistence.loader.tabDataSet.RowIdId2;
import persistence.loader.tabDataSet.RowIdNameDescription;

import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
     * Делаем изменения в DataSet ds в соответствие с данными объекта Object o. При изменение Object o, которым является  o.getClass()==Trest.class или o.getClass()==Work.class или o.getClass()==Machine.class ...
     * изменяется информация, которая касается его в DataSet ds
     * @param ds	база DataSet ds
     * @param o		Объект типа  o.getClass()==Trest.class или o.getClass()==Work.class или o.getClass()==Machine.class ...
     */
    static public  void FieldToField_ifClass(DataSet ds, Object o)
    {   

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


