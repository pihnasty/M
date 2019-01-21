package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

import java.util.ArrayList;

public class RowIdNameDescription implements Comparable<RowIdNameDescription> {

    public RowIdNameDescription(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public RowIdNameDescription() {
    }

    public RowIdNameDescription(DataSet dataSet, Class cL) {
        this(dataSet.IdMax(cL) + 1, cL.getSimpleName() + "№" + (dataSet.IdMax(cL) + 1), "Описание для    " + cL.getSimpleName());
    }

    public <cL> cL getById(Class cL, ArrayList<cL> tab, int id) {
        for (cL t : tab) {
            if (((RowIdNameDescription) t).getId() == id) {
                return t;
            }
        }
        return null;
    }
    /*
     * Данный метод использовать как приоритетный
     */

    public <cL> cL getById(Class cL, Object tab, int id) {
        if (tab != null) {
            for (cL t : (ArrayList<cL>) tab) {
                if (((RowIdNameDescription) t).getId() == id) {
                    return t;
                }
            }
        }
        return null;
    }

    
 //   
    /**
     * There  get  You  the last set minimum identifier for the table.
     * @param <cL>
     * @param cL
     * @param tab
     * @return id  There  get  You  the last set minimum identifier for the table  tab.
     */
    public <cL> int getMinId(Class cL, ArrayList<cL> tab) {
        Integer id = new Integer(Integer.MAX_VALUE);
        for (cL t : tab) {
            if (((RowIdNameDescription) t).getId() < id) {
                id = ((RowIdNameDescription) t).getId();
            }
        }
        return id;
    }

    public <cL> int getMinId(Class cL, Object tab) {
        Integer id = new Integer(Integer.MAX_VALUE);
        for (cL t : (ArrayList<cL>) tab) {
            if (((RowIdNameDescription) t).getId() < id) {
                id = ((RowIdNameDescription) t).getId();
            }
        }
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getName();
    }

    /* Id элемента									*/
    private int id;
    /* Имя элемента									*/
    private String name;
    /* Описание   элемента							*/
    private String description;

    @Override
    public int compareTo(RowIdNameDescription o) {
        if (this.getName().compareTo(((RowIdNameDescription) o).getName()) > 0) {  return 1;  }
        if (this.getName().compareTo(((RowIdNameDescription) o).getName()) < 0) {  return -1; }
        return 0;
    }
    public boolean equals(RowIdNameDescription o)	{
        if(o == this) 	return true;
        if(o == null)  	return false;
        if(!(getClass() == o.getClass())) 	return false;
        if( (o.getId() == this.getId()) )   return true;
        else return false;
    }

}
