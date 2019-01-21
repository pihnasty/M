package designpatterns;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class InitializableDS implements Initializable {
    protected ObservableDS observableDS;
    public InitializableDS (ObservableDS observableDS) {
        this.observableDS = observableDS;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}
