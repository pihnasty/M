package trestview.table;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.fxml.Initializable;
import trestview.table.tablemodel.TableModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pom on 28.02.2016.
 */
public class TableController extends InitializableDS {

    public TableController(ObservableDS observableDS) {
        super(observableDS);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
