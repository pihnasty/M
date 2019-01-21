package trestview.dictionary;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pom on 28.02.2016.
 */
public class DictionaryController extends InitializableDS {

    private DictionaryModel dictionaryModel;


    public DictionaryController (ObservableDS dictionaryModel) {
        super(dictionaryModel);
     }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
