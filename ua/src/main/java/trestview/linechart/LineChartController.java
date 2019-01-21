package trestview.linechart;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Maxim on 16.08.2016.
 */
public class LineChartController extends InitializableDS {

    public LineChartController(ObservableDS observableDS) {
        super(observableDS);
    }


}
