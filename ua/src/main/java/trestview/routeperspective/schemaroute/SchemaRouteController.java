package trestview.routeperspective.schemaroute;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class SchemaRouteController extends InitializableDS implements  EventHandler<MouseEvent> {

    public SchemaRouteController(ObservableDS observableDS) {
        super(observableDS);
    }

    @Override
    public void handle(MouseEvent event) {
/*
        if ( event.getEventType() == MouseEvent.MOUSE_MOVED) {
            ((SchemaRouteModel)observableDS).changeCursor(event);
        }
*/
        if ( event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            ((SchemaRouteModel)observableDS).changeLocation(event);
        }

        if ( event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            ((SchemaRouteModel)observableDS).qCurrentIsNull();
        }


    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
     * When the mouse cursor has been moved onto a component, the  image changes for cursor
     */

}
