package menu;

import designpatterns.InitializableDS;
import designpatterns.MVC;
import designpatterns.ObservableDS;
import fio.FileUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import main.AppProject;
import mainwindows.MainWindowView;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import settings.Settings;
import string.StringUtil;
import trestmodel.TrestModel;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController extends InitializableDS {


    private MenuModel menuModel;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem openItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem saveAsItem;
    @FXML
    private MenuItem exitItem;

    @FXML
    private MenuItem rawData;
    @FXML
    private MenuItem copyItem;
    @FXML
    private MenuItem pasteItem;

    @FXML
    private MenuItem defaultPerspectiveItem;
    @FXML
    private MenuItem orderPlaningPerspectiveItem;
    @FXML
    private MenuItem routesPerspectiveItem;
    @FXML
    private MenuItem resourcesLinksPerspectiveItem;
    @FXML
    private MenuItem  testOfMachineItem;
    @FXML
    private MenuItem  rowOperationItem;

    public MenuController(ObservableDS observableDS) {
        super(observableDS);
        this.menuModel = (MenuModel) observableDS;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveAsItem.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

//        rawData.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));
//        copyItem.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
//        pasteItem.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
//
//        defaultPerspectiveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+D"));
//        orderPlaningPerspectiveItem.setAccelerator(KeyCombination.keyCombination("Alt+O"));
//        routesPerspectiveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
//        resourcesLinksPerspectiveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));
//
//        testOfMachineItem.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
//        rowOperationItem.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));

    }

//------------------- menu File ------------------------------------
    @FXML
    private void handleOpenAction(ActionEvent event) {
        String pathToProject = FileUI.getPathToProject(
            ((AppProject)menuModel.getObservableDS()).getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.db.download")
        );
        ((AppProject)menuModel.getObservableDS()).getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH,pathToProject);
        ((AppProject)menuModel.getObservableDS()).openProject();
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
        ((AppProject)menuModel.getObservableDS()).saveProjectSettings();
    }

    @FXML
    private void handleSaveAsAction(ActionEvent event) {
        String pathToProject = FileUI.getSavePath(
            ((AppProject)menuModel.getObservableDS()).getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.db.save")
        );
        ((AppProject)menuModel.getObservableDS()).getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH,pathToProject);
        ((AppProject)menuModel.getObservableDS()).saveProjectSettings();
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        System.exit(0);
        Platform.exit();
    }

//------------------- menu Tasks ------------------------------------
@FXML
private void handleRawDataAction(ActionEvent event) {
    System.exit(0);
    Platform.exit();
}
//------------------- menu Tasks ------------------------------------
    @FXML
    private void handleConveyorSpeedConstantAction (ActionEvent event) {
        menuModel.clickConveyorSpeedConstantItem();
    }

    @FXML
    private void handleConveyorSpeedConstantControlBandAction (ActionEvent event) {
        menuModel.clickConveyorSpeedConstantControlBandItem();
    }

    @FXML
    private void handleConveyorSpeedDependsTimeAction (ActionEvent event) {
        menuModel.clickConveyorSpeedDependsTimeItem();
    }


//------------------- menu Dictionary ------------------------------------
    @FXML
    private void handleRowWorkAction (ActionEvent event) {
   //     new MVC(DictionaryModel.class, DictionaryController.class, DictionaryView.class, this.menuModel, MenuModel.Rule.RowWork  );
}
    @FXML
    private void handleRowTypemachineAction (ActionEvent event) {
  //      new MVC(DictionaryModel.class, DictionaryController.class, DictionaryView.class, this.menuModel, MenuModel.Rule.RowTypemachine );

    }
    @FXML
    private void handleRowFunctiondistAction (ActionEvent event) {
  //      new MVC(DictionaryModel.class, DictionaryController.class, DictionaryView.class, this.menuModel, MenuModel.Rule.RowFunctiondist );
    }

    @FXML
    private void handleRowUnitAction (ActionEvent event) {
  //      new MVC(DictionaryModel.class, DictionaryController.class, DictionaryView.class, this.menuModel, MenuModel.Rule.RowUnit );
    }
    @FXML
    private void handleRowOperationAction (ActionEvent event) {
  //      new MVC(DictionaryModel.class, DictionaryController.class, DictionaryView.class, this.menuModel, MenuModel.Rule.RowOperation );
    }

    //------------------- menu MachineTest ------------------------------------
    @FXML
    private void handleMachineTestAction (ActionEvent event) {    menuModel.clickTestOfMachineItem();   }

    //------------------- menu Window->OpenPerspective ------------------------------------
    @FXML
    private void handleResourcesLinksPerspectiveAction (ActionEvent event) { menuModel.clickResourcesLinksPerspectiveItem(); }

    @FXML
    private void handleRoutePerspectiveAction (ActionEvent event) { menuModel.clickRoutePerspectiveItem(); }

    @FXML
    private void handleOrderPlaningPerspectiveAction (ActionEvent event) { menuModel.clickOrderPlaninigPerspectiveItem(); }


    /**
     * Add congig.xml
     * @param pathData          The directory path to the database, which (path) will be written to the file (tSettings).
     * @param boolOpenDataSet   If it is true - a database in the specified path is read into the model.
     */
    public void savePathConfig(String pathData, boolean boolOpenDataSet) {
//        if (pathData != "") {
//            menuModel.getTrestModel().getDataSet().setPathDataDefault(pathData);                    // Set the default path to the database.
//            menuModel.getTrestModel().getDataSet().tSettings.get(0).setSystemPath(pathData);        // We set up a new path.
//            menuModel.getTrestModel().getDataSet().setPathDataDefault(DataSet.getPathConfig());
//            menuModel.getTrestModel().getDataSet().writeTab(DataSet.tSettings);                     // Write the changes to the file [tSettings]
//            menuModel.getTrestModel().getDataSet().setPathDataDefault(pathData);
//            if (boolOpenDataSet) {
//                TrestModel trestModel = new TrestModel(null,null);           //  We get a new model from a the changed directory.
//                menuModel.setTrestModel(trestModel);                //  We define a new model
//            }
//        }
    }
}
