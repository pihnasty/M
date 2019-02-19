package menu;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import dialogs.AlertDialog;
import fio.FileUI;
import hct.handlers.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import main.ProjectManager;
import mainwindows.MainWindowView;


import java.net.URL;
import java.util.ResourceBundle;

import static settings.Settings.Values.TEMPLATE_EXPERIMENT_PLAN_JSON;

public class MenuController extends InitializableDS {


    private MenuModel menuModel;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem newItem;

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
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
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
    private void handleNewAction(ActionEvent event) {
        new CreateProjectHandler().createProject(menuModel.getObservableDS());
    }

    @FXML
    private void handleOpenAction(ActionEvent event) {
        new OpenProjectHandler().openProject(menuModel.getObservableDS());
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
        new SaveProjectHandler().saveProject(menuModel.getObservableDS());
    }

    @FXML
    private void handleSaveAsAction(ActionEvent event) {
        new SaveAsProjectHandler().saveAsProject(menuModel.getObservableDS());
    }

    @FXML
    private void handleExitAction(ActionEvent event) {
        System.exit(0);
        Platform.exit();
    }

//------------------- menu Prepare ------------------------------------
    @FXML
    private void handleRawDataAction(ActionEvent event) {
        new UploadRawDataHandler().uploadRawData(menuModel.getObservableDS());
    }

    @FXML
    private void handleNameCategoryAction(ActionEvent event) {
        new UploadNameCategoryHandler().uploadNameCategory(menuModel.getObservableDS());
    }

    @FXML
    private void handleSeparatedRawDataAction(ActionEvent event) {
        new SeparatedRawDataHandler().separatedRawData(menuModel.getObservableDS());


//        if(!((ProjectManager)menuModel.getObservableDS()).separatedRawData()) {
//            AlertDialog.getAlert(MainWindowView.loaderRecource.getResources().getString("title.message.alert.separated.rawData")
//                , MainWindowView.loaderRecource.getResources().getString("message.alert.separated.rawData"));
//        }
    }
//------------------- menu Analysis ------------------------------------
    @FXML
    private void handleCalculateFactorsAction (ActionEvent event) {
        new CreateFactorsHandler().createFactors(menuModel.getObservableDS());
    }

    @FXML
    private void handleDownloadExperimentPlanAction(ActionEvent event) {
        new DownloadExperimentPlanHandler().downloadExperimentPlan(menuModel.getObservableDS());
    }

    @FXML
    private void handleCreateTemplateExperimentPlanAction(ActionEvent event) {
        new CreateTemplateExperimentPlanHandler().createTemplateExperimentPlan(menuModel.getObservableDS());
    }


    //------------------- menu Analysis->One.factor.model ------------------------------------
    @FXML
    private void handleCalculateCoefficientsOneParameterModelAction (ActionEvent event) {
        ((ProjectManager)menuModel.getObservableDS()).calculateCoefficientsOneParameterModel_a_b();
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
    ((ProjectManager)menuModel.getObservableDS()).fastStart();
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
