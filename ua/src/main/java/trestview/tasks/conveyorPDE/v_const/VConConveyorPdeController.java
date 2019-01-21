package trestview.tasks.conveyorPDE.v_const;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.scene.control.Label;


public class VConConveyorPdeController extends InitializableDS
{
    private Label label;

    public VConConveyorPdeController(ObservableDS observableDS) {
        super(observableDS);
    }


    public void setLabel(Label label) {
        this.label = label;
    }


}
