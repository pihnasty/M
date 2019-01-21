package trestview.routeperspective;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;

/**
 * Created by Max on 17.09.2016.
 */
public class RoutePerspectiveController extends InitializableDS {
    public RoutePerspectiveController(ObservableDS observableDS) {
        super(observableDS);
    }
}
