package trestview.resourcelink;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.fxml.Initializable;
import trestview.dictionary.DictionaryModel;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by pom on 28.02.2016.
 */
public class ResourceLinkController extends InitializableDS {


    public ResourceLinkController (ObservableDS resourceLinkModel) {
        super(resourceLinkModel);
     }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}
