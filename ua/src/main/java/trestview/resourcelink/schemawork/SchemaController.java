package trestview.resourcelink.schemawork;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;


import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;


public class SchemaController extends InitializableDS implements  EventHandler<MouseEvent> {
    private SchemaModel observableModel;

    public SchemaController(ObservableDS observableDS) {
        super(observableDS);
    }


    @Override
    public void handle(MouseEvent event) {

        if ( event.getEventType() == MouseEvent.MOUSE_MOVED) {
            ((SchemaModel)observableDS).changeCursor(event);
        }

        if ( event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            ((SchemaModel)observableDS).changeLocation(event);
        }

        if ( event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            ((SchemaModel)observableDS).qCurrentIsNull();
        }


    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     * When the mouse cursor has been moved onto a component, the  image changes for cursor
     */

}
