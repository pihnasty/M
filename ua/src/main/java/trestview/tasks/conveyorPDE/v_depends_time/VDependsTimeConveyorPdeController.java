package trestview.tasks.conveyorPDE.v_depends_time;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.scene.control.Label;


public class VDependsTimeConveyorPdeController extends InitializableDS
{
    private Label label;

    public VDependsTimeConveyorPdeController(ObservableDS observableDS) {
        super(observableDS);
    }


    public void setLabel(Label label) {
        this.label = label;
    }


}
