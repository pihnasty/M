package trestview.orderplaninigperspective.schemaroute;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class SchemaOrderPlaninigController extends InitializableDS implements  EventHandler<MouseEvent> {

    public SchemaOrderPlaninigController(ObservableDS observableDS) {
        super(observableDS);
    }

    @Override
    public void handle(MouseEvent event) {

        if ( event.getEventType() == MouseEvent.MOUSE_MOVED) {
            ((SchemaOrderPlaninigModel)observableDS).changeCursor(event);
        }

        if ( event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            ((SchemaOrderPlaninigModel)observableDS).changeLocation(event);
        }

        if ( event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            ((SchemaOrderPlaninigModel)observableDS).qCurrentIsNull();
        }


    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     * When the mouse cursor has been moved onto a component, the  image changes for cursor
     */

}
