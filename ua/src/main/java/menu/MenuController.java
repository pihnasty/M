package menu;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import dialogs.AlertDialog;
import fio.FileUI;
import hct.handlers.*;
import hct.handlers.multi.CalculateCoefficientsMultiParameterModelHandler;
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
    ObservableDS modelObservableDS;

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
    private MenuItem saveGraphToPdfItem;

    @FXML
    private MenuItem buildDataGraphTwoModelItem;

    @FXML
    private MenuItem twoModelSaveGraphToPdfItem;

    @FXML
    private MenuItem twoFactorProbitBoundaryValueGraphItem;

    @FXML
    private MenuItem twoFactorProbitBoundaryValueSaveGraphToPdfItem;

    @FXML
    private MenuItem twoFactorProbitBuildGraphItem;

    @FXML
    private MenuItem twoFactorProbitGraphSaveToPdfItem;

    @FXML
    private MenuItem buildResidualPlotMultiModelItem;

    @FXML
    private MenuItem calculateCoefficientsMultiParameterModelItem;

    @FXML
    private MenuItem fastStartItem;

    public MenuController(ObservableDS observableDS) {
        super(observableDS);
        this.menuModel = (MenuModel) observableDS;
        this.modelObservableDS=menuModel.getObservableDS();
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
        saveGraphToPdfItem.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));
        buildDataGraphTwoModelItem.setAccelerator(KeyCombination.keyCombination("Ctrl+3"));
        twoModelSaveGraphToPdfItem.setAccelerator(KeyCombination.keyCombination("Ctrl+4"));
        twoFactorProbitBoundaryValueGraphItem.setAccelerator(KeyCombination.keyCombination("Ctrl+5"));
        twoFactorProbitBoundaryValueSaveGraphToPdfItem.setAccelerator(KeyCombination.keyCombination("Ctrl+6"));
        twoFactorProbitBuildGraphItem.setAccelerator(KeyCombination.keyCombination("Ctrl+7"));
        twoFactorProbitGraphSaveToPdfItem.setAccelerator(KeyCombination.keyCombination("Ctrl+8"));
        buildResidualPlotMultiModelItem.setAccelerator(KeyCombination.keyCombination("Ctrl+9"));
        calculateCoefficientsMultiParameterModelItem.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));
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
        new CalculateCoefficientsOneParameterModel_a_bHandler().create_a_b(menuModel.getObservableDS());
    }

    @FXML
    private void handleRawDataGraphAction(ActionEvent event) {
            new CreateChartScrollPaneHandler().buildGraph(modelObservableDS);
    }

    @FXML
    private void handleSaveToPdfAction(ActionEvent event) {
         new CreateChartScrollPaneHandler().saveToPDF(modelObservableDS);
    }

    @FXML
    private void handleDefaultAction(ActionEvent event) {
        new DefaultScrollPaneHandler().clean(modelObservableDS);
    }

    //------------------- menu Analysis->Two.factor.model ------------------------------------
    @FXML
    private void handlebuildDataGraphTwoModelAction(ActionEvent event) {
        new CreateChartScrollPaneTwoModelHandler().buildGraph(modelObservableDS);
    }

    @FXML
    private void handleTwoModelSaveToPdfAction(ActionEvent event) {
        new CreateChartScrollPaneTwoModelHandler().saveToPDF(modelObservableDS);
    }

    @FXML
    private void handleDefaultTwoParameterModelAction(ActionEvent event) {
        new DefaultScrollPaneHandler().clean(modelObservableDS);
    }

    //------------------- menu Analysis->Two.factor.model->Probit ------------------------------------

    @FXML
    private void handletwoFactorProbitBoundaryValueGraphAction(ActionEvent event) {
        new CreateChartScrollPaneTwoModelBoundaryProbitHandler().buildGraph(modelObservableDS);
    }

    @FXML
    private void handletwoFactorProbitBoundaryValueSaveToPdfAction(ActionEvent event) {
        new CreateChartScrollPaneTwoModelBoundaryProbitHandler().saveToPDF(modelObservableDS);
    }

    @FXML
    private void handletwoFactorProbitBuildGraphAction(ActionEvent event) {
         new CreateChartScrollPaneTwoModelProbitHandler().buildGraph(modelObservableDS);
    }

    @FXML
    private void handletwoFactorProbitSaveGraphAction(ActionEvent event) {
        new CreateChartScrollPaneTwoModelProbitHandler().saveToPDF(modelObservableDS);
    }

    //------------------- menu Analysis->Multi.factor.model ------------------------------------
    @FXML
    private void handleCalculateCoefficientsMultiParameterModelAction(ActionEvent event) {
        new CalculateCoefficientsMultiParameterModelHandler().calculate(modelObservableDS);
    }

    @FXML
    private void handleBuildResidualPlotMultiModelAction(ActionEvent event) {
        new CreateResidualPlotMultiModelHandler().buildResidualPlot(modelObservableDS);
    }


    //------------------- menu Settings ------------------------------------
@FXML
private void handleFastStartAction(ActionEvent event) {
    ((ProjectManager)menuModel.getObservableDS()).fastStart();
}

}
