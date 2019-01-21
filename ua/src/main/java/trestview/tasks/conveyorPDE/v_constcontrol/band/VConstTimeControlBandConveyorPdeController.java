package trestview.tasks.conveyorPDE.v_constcontrol.band;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.scene.control.Label;


public class VConstTimeControlBandConveyorPdeController extends InitializableDS
{
    private Label label;

    public VConstTimeControlBandConveyorPdeController(ObservableDS observableDS) {
        super(observableDS);
    }


    public void setLabel(Label label) {
        this.label = label;
    }


}
