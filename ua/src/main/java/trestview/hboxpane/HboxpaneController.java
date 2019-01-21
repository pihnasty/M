package trestview.hboxpane;


import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import persistence.loader.XmlRW;
//import resources.images.icons.IconT;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by pom on 07.02.2016.
 */
public class HboxpaneController extends InitializableDS {

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button delButton;

    private HboxpaneModel hboxpaneModel;

     public HboxpaneController(ObservableDS observableDS) {
        super(observableDS);
        this.hboxpaneModel = (HboxpaneModel)observableDS;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButton. setGraphic(new ImageView(new Image("file:pde\\src\\main\\resources\\images\\icons\\add.png" )));
        editButton. setGraphic(new ImageView(new Image("file:pde\\src\\main\\resources\\images\\icons\\edit.png" )));
        saveButton. setGraphic(new ImageView(new Image("file:pde\\src\\main\\resources\\images\\icons\\save.png" )));
        delButton. setGraphic(new ImageView(new Image("file:pde\\src\\main\\resources\\images\\icons\\del.png" )));
    }


    @FXML
    public void handleAddAsAction(MouseEvent e )  { hboxpaneModel.addRowTable();  }
    @FXML
    public void handleSaveAsAction(MouseEvent e ) { hboxpaneModel.saveRowTable(); }
    @FXML
    public void handleEditAsAction(MouseEvent e ) { hboxpaneModel.editRowTable(); }
    @FXML
    public void handleDelAsAction(MouseEvent e )  { hboxpaneModel.delRowTable(); }

}
