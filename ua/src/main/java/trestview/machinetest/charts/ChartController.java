package trestview.machinetest.charts;

import javafx.fxml.Initializable;
import trestview.machinetest.MachineTestModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Роман on 15.04.2016.
 */
public class ChartController implements Initializable {

    private ChartCommonModel chartCommonModel;

    public ChartController(ChartCommonModel chartCommonModel1) {
        this.chartCommonModel = chartCommonModel1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
