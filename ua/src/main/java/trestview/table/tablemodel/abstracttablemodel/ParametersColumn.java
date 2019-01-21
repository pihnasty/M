package trestview.table.tablemodel.abstracttablemodel;

import persistence.loader.XmlRW;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

/**
 * column settings
 */
public class ParametersColumn  {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFielgName() {
        return fieldName;
    }

    public Class getcLs() {
        return cLs;
    }

    public void setcLs(Class cLs) {
        this.cLs = cLs;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    private String name;
    private String fieldName;   // Field names for [RowIdNameDescription (int id, String name, String description)] ->  [id, name, description)]
    private Class cLs;
    private boolean editable;
    private double  width;

    public ParametersColumn (String fieldName, Class cLs, boolean editable, double  width) {
        this.fieldName=fieldName;
        this.name=getHeader(fieldName);
        this.cLs = cLs;
        this.editable = editable;
        this.width = width;
     }

    private String getHeader(String headerColumn){
        return ResourceBundle.getBundle("ui").getString(headerColumn);
    }
}