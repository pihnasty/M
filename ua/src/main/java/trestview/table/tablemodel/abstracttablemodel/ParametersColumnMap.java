package trestview.table.tablemodel.abstracttablemodel;

import entityProduction.Modelmachine;
import javafx.scene.image.Image;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by pom on 19.03.2016.
 */
public class ParametersColumnMap  {
    private static Map<String,ParametersColumn> map;
    private ParametersColumnMap () {
        map = new HashMap<>();
        int sizeName = 50;
        int sizeQuantity = (int) (sizeName*0.5);

        // Parameters  of column  for RowWork
        map.put("id", new ParametersColumn ("id",      int.class, false, 40) );
        map.put("name", new ParametersColumn ("name", String.class, true, 150) );
        map.put("scheme", new ParametersColumn ("scheme", String.class, true, 200) );
        map.put("overallSize", new ParametersColumn ("overallSize", double.class, true, 90) );
        map.put("scaleEquipment", new ParametersColumn ("scaleEquipment", double.class, true, 90) );
        map.put("description", new ParametersColumn ("description",String.class, true, 300) );
        map.put("image", new ParametersColumn ("image",Image.class, true, 100) );
        // Parameters  of column  for RowMachine
        map.put("locationX", new ParametersColumn ("locationX",double.class, true, 100) );
        map.put("locationY", new ParametersColumn ("locationY",double.class, true, 100) );
        map.put("angle", new ParametersColumn ("angle",double.class, true, 100) );
        map.put("state", new ParametersColumn ("state",double.class, true, 100) );
        // Parameters  of column  for Machine
        map.put("modelmachine", new ParametersColumn ("modelmachine", Modelmachine.class, true, 100) );
        // Parameters  of column  for Subject_labour
        map.put("price", new ParametersColumn ("price", double.class, true, 100) );
        map.put("unitName", new ParametersColumn ("unitName", String.class, true, 40) );
        // Parameters  of column  for Functiondist
        map.put("averageValue", new ParametersColumn ("averageValue",double.class, true, 100) );
        map.put("meanSquareDeviation", new ParametersColumn ("meanSquareDeviation",double.class, true, 100) );
        map.put("pathData", new ParametersColumn ("pathData", String.class, true, 200) );
        // Parameters  of column  for Lineroute
        map.put("nameOperation", new ParametersColumn ("nameOperation", String.class, true, 130) );
        map.put("nameMachine", new ParametersColumn ("nameMachine", String.class, true, 130) );
        map.put("numberWork", new ParametersColumn ("numberWork",      int.class, true, 50) );
        map.put("inputBufferMin", new ParametersColumn ("inputBufferMin",      int.class, true, 50) );
        map.put("inputBuffer", new ParametersColumn ("inputBuffer",      int.class, true, 50) );
        map.put("inputBufferMax", new ParametersColumn ("inputBufferMax",      int.class, true, 50) );
        map.put("outputBufferMin", new ParametersColumn ("outputBufferMin",      int.class, true, 50) );
        map.put("outputBuffer", new ParametersColumn ("outputBuffer",      int.class, true, 50) );
        map.put("outputBufferMax", new ParametersColumn ("outputBufferMax",      int.class, true, 50) );
        // Parameters  of column  for Linespec
        map.put("resourceName", new ParametersColumn ("resourceName", String.class, true, 130) );
        map.put("m", new ParametersColumn ("m",double.class, true, 50) );
        map.put("sigma", new ParametersColumn ("sigma",double.class, true, 50) );
        map.put("functionOEMName", new ParametersColumn ("functionOEMName", String.class, true, 130) );
        // Parameters  of column  for Order
        map.put("dateBegin", new ParametersColumn ("dateBegin",Date.class, true, 50) );
        map.put("dateEnd", new ParametersColumn ("dateEnd",Date.class, true, 50) );
        // Parameters  of column  for Line
        map.put("subject_labourName", new ParametersColumn ("subject_labourName",String.class, true, sizeName) );
        map.put("quantity", new ParametersColumn ("quantity",double.class, true, sizeQuantity) );

    }

    public static ParametersColumn getParametersColumn(String key) {
        new ParametersColumnMap ();
        return map.get(key);
    }

}