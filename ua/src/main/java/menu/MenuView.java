package menu;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import persistence.loader.XmlRW;

import java.util.Observable;
import java.util.Observer;

public class MenuView extends MenuBar  implements Observer {

    private ObservableDS menuModel;

    @FXML
    private Menu OpenPerspective2;

    @FXML
    private Menu OpenPerspective;

    public MenuView() {
    }

    public MenuView(ObservableDS menuModel, InitializableDS menuController ) {
        this.menuModel =menuModel;
        XmlRW.fxmlLoad (this, menuController, "menu/menuview.fxml", "ui", "menu/stylemenu.css");
    }

    @Override
    public void update(Observable o, Object arg) {
        menuModel = (MenuModel)o;
    }
}
