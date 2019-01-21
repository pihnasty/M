package trestcontroller;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;

/**
 * Created by Max on 19.02.2016.
 */
public class TrestController extends InitializableDS{

    public TrestController(ObservableDS observableDS) {
        super(observableDS);
    }
}
