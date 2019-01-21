package trestview.machinetest;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import entityProduction.Machine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Roman Korsun on 22.03.2016.
 */
public class MachineTestController extends InitializableDS {
    private MachineTestModel machineTestModel;

    //stay tuned
    public MachineTestController(MachineTestModel model) {
        this((ObservableDS)model);
    }
    public MachineTestController(ObservableDS machineTestModel) {
        super(machineTestModel);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
