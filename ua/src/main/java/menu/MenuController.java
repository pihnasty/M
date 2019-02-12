package menu;

import designpatterns.InitializableDS;
import designpatterns.MVC;
import designpatterns.ObservableDS;
import dialogs.AlertDialog;
import fio.FileUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import main.AppProject;
import mainwindows.MainWindowView;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import settings.Settings;


import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static settings.Settings.Values.TEMPLATE_EXPERIMENT_PLAN_JSON;

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
    private MenuItem rowDataItem;

    @FXML
    private MenuItem nameCategoryItem;

    @FXML
    private MenuItem separatedRowDataItem;

    @FXML
    private MenuItem calculateFactorsItem;

    @FXML
    private MenuItem downloadExperimentPlanItem;

    @FXML
    private MenuItem createTemplatePlanItem;

    @FXML
    private MenuItem calculateCoefficientsOneParameterModelItem;

    @FXML
    private MenuItem rawDataGraphItem;

    @FXML
    private MenuItem fastStartItem;

    @FXML
    private MenuItem orderPlaningPerspectiveItem;
    @FXML
    private MenuItem routesPerspectiveItem;
    @FXML
    private MenuItem resourcesLinksPerspectiveItem;
    @FXML
    private MenuItem  testOfMachineItem;


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

        rowDataItem.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));
        nameCategoryItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        separatedRowDataItem.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
        calculateFactorsItem.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
        calculateCoefficientsOneParameterModelItem.setAccelerator(KeyCombination.keyCombination("Ctrl+1"));
        rawDataGraphItem.setAccelerator(KeyCombination.keyCombination("Ctrl+2"));
        downloadExperimentPlanItem.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
        createTemplatePlanItem.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));

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
        ((AppProject)menuModel.getObservableDS()).saveRawData();
    }

    @FXML
    private void handleSaveAsAction(ActionEvent event) {
        String pathToProject = FileUI.getSavePath(
            ((AppProject)menuModel.getObservableDS()).getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.db.save")
        );
        ((AppProject)menuModel.getObservableDS()).getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH,pathToProject);
        ((AppProject)menuModel.getObservableDS()).saveProjectSettings();
        ((AppProject)menuModel.getObservableDS()).saveRawData();
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        System.exit(0);
        Platform.exit();
    }

//------------------- menu Prepare ------------------------------------
    @FXML
    private void handleRawDataAction(ActionEvent event) {
        String pathToFile = FileUI.getPathToFile(
            ((AppProject)menuModel.getObservableDS()).getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.db.download")
        );
        ((AppProject)menuModel.getObservableDS()).uploadRawData(pathToFile);
    }

    @FXML
    private void handleNameCategoryAction(ActionEvent event) {
        String pathToFile = FileUI.getPathToFile(
            ((AppProject)menuModel.getObservableDS()).getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.file.category.name.download")
        );
        ((AppProject)menuModel.getObservableDS()).uploadNameCategory(pathToFile);
    }

    @FXML
    private void handleSeparatedRawDataAction(ActionEvent event) {
        if(!((AppProject)menuModel.getObservableDS()).separatedRawData()) {
            AlertDialog.getAlert(MainWindowView.loaderRecource.getResources().getString("title.message.alert.separated.rawData")
                , MainWindowView.loaderRecource.getResources().getString("message.alert.separated.rawData"));
        }
    }
//------------------- menu Analysis ------------------------------------
    @FXML
    private void handleCalculateFactorsAction (ActionEvent event) {

        ((AppProject)menuModel.getObservableDS()).createFactors ();
    }

    @FXML
    private void handleDownloadExperimentPlanAction(ActionEvent event) {
        String pathToFile = FileUI.getPathToFile(
            ((AppProject)menuModel.getObservableDS()).getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.file.category.name.download")
        );
        ((AppProject) menuModel.getObservableDS()).downloadExperimentPlan(pathToFile);
    }

    @FXML
    private void handleCreateTemplateExperimentPlanAction(ActionEvent event) {
        String pathToFile = FileUI.getSavePath(
            ((AppProject)menuModel.getObservableDS()).getProjectPath()
            , MainWindowView.loaderRecource.getResources().getString("select.db.save")
        );
        ((AppProject) menuModel.getObservableDS()).createTemplateExperimentPlan(pathToFile+"//"+TEMPLATE_EXPERIMENT_PLAN_JSON );
    }


    //------------------- menu Analysis->One.factor.model ------------------------------------
    @FXML
    private void handleCalculateCoefficientsOneParameterModelAction (ActionEvent event) {
        ((AppProject)menuModel.getObservableDS()).calculateCoefficientsOneParameterModel_a_b();
    }

    @FXML
    private void handleRawDataGraphItemAction(ActionEvent event) {
        menuModel.buildRawDataGraph();
    }

    @FXML
    private void handleDefaultItemAction(ActionEvent event) {
        menuModel.defaultAction();
    }


//------------------- menu Settings ------------------------------------
@FXML
private void handleFastStartAction(ActionEvent event) {
    ((AppProject)menuModel.getObservableDS()).fastStart();
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

}
