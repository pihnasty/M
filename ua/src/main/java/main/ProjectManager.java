package main;

import com.sun.javafx.print.PrinterJobImpl;
import designpatterns.ObservableDS;
import experiment.Plan;
import factors.FactorManager;
import fio.FileUI;
import io.csv.read.CsvReaderP;
import io.csv.write.CsvWriterP;
import io.gson.read.Reader;
import io.gson.write.Writer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import logging.LoggerP;
import models.OneParameterModel;
import settings.EnumSettings;
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;



public class ProjectManager extends ObservableDS {
    private static ProjectManager ourInstance = new ProjectManager();

   private AppProject project;

    public static ProjectManager getInstance() {
        return ourInstance;
    }

    private Settings defaultSettings = ProviderSettings.getSettings(EnumSettings.DEFAULT);
    private Settings projectSettings = ProviderSettings.getSettings(EnumSettings.PROJECT);
    private Settings globalSettings = ProviderSettings.getSettings(EnumSettings.GLOBAL);

    private Plan planExperiment = planExperiment = Plan.getInstance();

    private Object scrollPane;

    private Function<Boolean, ScrollPane> functionScrollPane;



    private ProjectManager() {

    }

    public void openProject(String pathToProject) throws FileNotFoundException {
        LoggerP.logger.log(Level.SEVERE, "openProject", "LoggerP");
        getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH,pathToProject);
        openProjectSettings();
        openTableData();
    }

    public void openTableData() throws FileNotFoundException {
        project.setRawDataTable(openData(Settings.Values.RAW_DATA_TABLE_CSV ));
        project.setNameCategoryTable(openData(Settings.Values.NAME_CATEGORY_TABLE_CSV));
        project.setSeparatedRawDataTable(openData(Settings.Values.SEPARATED_RAW_DATA_TABLE_CSV ));
        project.setTestedRawDataTable(openData(Settings.Values.TESTED_RAW_DATA_TABLE_CSV));

        project.setNormalizedSeparatedRawDataTable(openData(Settings.Values.NORMALIZED_SEPARATED_RAW_DATA_TABLE_CSV));
        project.setCovarianceCoefficients(openData(Settings.Values.COVARIANCE_COEFFICIENTS_SEPARATED_RAW_DATA_TABLE_CSV));
        project.setSignificanceOfFactors(openData(Settings.Values.SIGNIFICANCE_FACTORS_SEPARATED_RAW_DATA_TABLE_CSV));

        project.setCharacteristicsSeparatedRawDataTable(openData(Settings.Values.CHARACTERISTICS_SEPARATED_RAW_DATA_TABLE_CSV));
        project.setCharacteristicsDimensionlessSeparatedRawDataTable(openData(Settings.Values.CHARACTERISTICS_DIMENSIONLESS_SEPARATED_RAW_DATA_TABLE_CSV));

        planExperiment = (Plan) openDataJson(Settings.Values.EXPERIMENT_PLAN_JSON, planExperiment);

        project.setKoefficientA(openData(Settings.Values.ONE_PARAMETER_MODEL_KOEF_A_TABLE_CSV));
        project.setKoefficientB(openData(Settings.Values.ONE_PARAMETER_MODEL_KOEF_B_TABLE_CSV));
        project.setMultiModelDimensionlessKoefficients(openData(Settings.Values.MULTI_MODEL_DIMENSION_LESS_KOEF_CSV));
        project.setMultiModelDimensionKoefficients(openData(Settings.Values.MULTI_MODEL_DIMENSION_KOEF_CSV));

    }

    private List<List<String>> openData(String fileName) {
        String path = io.file.Paths.getPathToDirectory(getProjectPath()+"//"+fileName);
        fileName = io.file.Paths.getShortFileName(getProjectPath()+"//"+fileName);
        return readTableFromFile(path +"//"+fileName);
    }

    private Object openDataJson(String fileName, Object objectFromGson) throws FileNotFoundException {
       // String path = io.file.Paths.getPathToDirectory(getProjectPath()+"//"+fileName);
       // fileName = io.file.Paths.getShortFileName(getProjectPath()+"//"+fileName);
        return  Reader.readFromGsonFile(getProjectPath()+"//"+fileName, objectFromGson);
    }

    public void openProjectSettings() {
        projectSettings.setMap(openDataSetting(getProjectPath(), EnumSettings.PROJECT.getFileName()));
        globalSettings.setMap(openDataSetting(getProjectPath(), EnumSettings.GLOBAL.getFileName()));
    }

    public Map<String, String> openDataSetting(String projectPath, String fileName) {
        String path = io.file.Paths.getPathToDirectory(projectPath+"//"+fileName);
        fileName = io.file.Paths.getShortFileName(projectPath+"//"+fileName);
        Map<String, String> dataSetting = EnumSettings.readSettingsFromFile(path, fileName);
        dataSetting.put(Settings.Keys.PROJECT_PATH ,projectPath);
        return dataSetting;
    }

    public void saveProject() {
        saveProjectSettings();
        saveTableData();
    }

    public void saveAsProject(String pathToProject) {
        getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH, pathToProject.replace("\\","//"));
        getProjectSettings().getMap().put(Settings.Keys.PROJECT_PATH, pathToProject.replace("\\","//"));
        saveProject();
    }

    public Settings getDefaultSettings() {
        return defaultSettings;
    }

    public Settings getProjectSettings() {
        return projectSettings;
    }

    public Settings getGlobalSettings() {
        return globalSettings;
    }

    public String getProjectPath() {
        return StringUtil.OptionalIsNullOrEmpty(
             getDefaultSettings().getMap().get(Settings.Keys.PROJECT_PATH)
            ,getDefaultSettings().getMap().get(Settings.Keys.DEFAULT_PROJECT_PATH)
        );
    }

    public void saveProjectSettings() {
        saveDataSetting(defaultSettings, Settings.Values.DEFAULT_PROJECT_PATH, EnumSettings.DEFAULT.getFileName());
        saveDataSetting(projectSettings, getProjectPath(), EnumSettings.PROJECT.getFileName());
        saveDataSetting(globalSettings, getProjectPath(), EnumSettings.GLOBAL.getFileName());
    }

    public void saveDataSetting(Settings settings, String projectPath, String fileName) {
        String path = io.file.Paths.getPathToDirectory(projectPath+"//"+fileName);
        fileName = io.file.Paths.getShortFileName(projectPath+"//"+fileName);

        CsvWriterP csvWriterP = new CsvWriterP("%8.3f ", ';', path, fileName);
        if (Objects.nonNull(settings)) {
            csvWriterP.writeToFile(
                settings.getMap().entrySet().stream().map(e -> new ArrayList<>(Arrays.asList(e.getKey(), e.getValue()))).collect(Collectors.toList())
            );
        }
    }


    public void uploadRawData(String fullFileName) {
        project.setRawDataTable(readTableFromFile(fullFileName));
    }

    public void uploadNameCategory(String fullFileName) {
        project.setNameCategoryTable(readTableFromFile(fullFileName));
    }

    public List<List<String>> readTableFromFile(String fullFileName) {
        List<List<String>> table =null;
        if (Objects.nonNull(fullFileName)) {
            CsvReaderP csvReaderP = new CsvReaderP("%8.3f ", ';'
                , FileUI.getPathToDirictory(fullFileName), FileUI.getShortFileName(fullFileName));

            try {
                table = csvReaderP.readFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return table;
    }

    public void saveTableData() {
        saveData(project.getRawDataTable(),Settings.Values.RAW_DATA_TABLE_CSV );
        saveData(project.getNameCategoryTable(),Settings.Values.NAME_CATEGORY_TABLE_CSV);
        saveData(project.getSeparatedRawDataTable(),Settings.Values.SEPARATED_RAW_DATA_TABLE_CSV );
        saveData(project.getTestedRawDataTable(),Settings.Values.TESTED_RAW_DATA_TABLE_CSV );

        saveData(project.getNormalizedSeparatedRawDataTable(),Settings.Values.NORMALIZED_SEPARATED_RAW_DATA_TABLE_CSV);
        saveData(project.getCovarianceCoefficients(),Settings.Values.COVARIANCE_COEFFICIENTS_SEPARATED_RAW_DATA_TABLE_CSV);
        saveData(project.getSignificanceOfFactors(),Settings.Values.SIGNIFICANCE_FACTORS_SEPARATED_RAW_DATA_TABLE_CSV);
        saveData(project.getCharacteristicsSeparatedRawDataTable(),Settings.Values.CHARACTERISTICS_SEPARATED_RAW_DATA_TABLE_CSV);
        saveData(project.getCharacteristicsDimensionlessSeparatedRawDataTable(),Settings.Values.CHARACTERISTICS_DIMENSIONLESS_SEPARATED_RAW_DATA_TABLE_CSV);

        saveDataJson( Settings.Values.EXPERIMENT_PLAN_JSON, planExperiment);
        saveData(project.getKoefficientA(),Settings.Values.ONE_PARAMETER_MODEL_KOEF_A_TABLE_CSV);
        saveData(project.getKoefficientB(),Settings.Values.ONE_PARAMETER_MODEL_KOEF_B_TABLE_CSV);
        saveData(project.getMultiModelDimensionlessKoefficients(),Settings.Values.MULTI_MODEL_DIMENSION_LESS_KOEF_CSV);
        saveData(project.getMultiModelDimensionKoefficients(),Settings.Values.MULTI_MODEL_DIMENSION_KOEF_CSV);




}

    private void saveData(List<List<String>> dataTable, String fileName) {

        String path = io.file.Paths.getPathToDirectory(getProjectPath()+"//"+fileName);
        fileName = io.file.Paths.getShortFileName(getProjectPath()+"//"+fileName);

        CsvWriterP csvWriterP = new CsvWriterP("%8.3f ", ';'
            , path, fileName);
        if (Objects.nonNull(dataTable)) {
            csvWriterP.writeToFile(dataTable);
        }
    }

    private void saveDataJson( String fileName, Object objectToGson) {
        String path = io.file.Paths.getPathToDirectory(getProjectPath()+"//"+fileName);
        fileName = io.file.Paths.getShortFileName(getProjectPath()+"//"+fileName);
        Writer.saveToGsonFile( path,fileName, objectToGson);
    }

    public boolean separatedRawData() {
        project.separatedRawData(projectSettings,defaultSettings);
        return true;
    }

    public void createFactors() {
        project.createFactors();
    }

    public void calculateCoefficientsOneParameterModel_a_b() {
        project.calculateCoefficientsOneParameterModel_a_b();
    }

    public void calculateCoefficientsMultiParameterModel() {
        project.calculateCoefficientsMultiParameterModel(
            getPlanExperiment().getOutputFactors()
            , getPlanExperiment().getInputFactors()
            , getPlanExperiment().getParametersOfModel()
        );
    }

    public void downloadExperimentPlan(String fullFileName) throws FileNotFoundException {
        planExperiment =(Plan) Reader.readFromGsonFile(fullFileName, planExperiment);
    }

    public void createTemplateExperimentPlan(String fullFileName) {
        planExperiment = Plan.getDefaultPlan();
        Writer.saveToGsonFile(fullFileName, planExperiment);
    }

    public void fastStart() {

    }

    public Plan getPlanExperiment() {
        return planExperiment;
    }

    public void setPlanExperiment(Plan planExperiment) {
        this.planExperiment = planExperiment;
    }

    public AppProject getProject() {
        return project;
    }

    public void setProject(AppProject project) {
        this.project = project;
    }

    public void setProjectSettings(Settings projectSettings) {
        this.projectSettings = projectSettings;
    }

    public void setGlobalSettings(Settings globalSettings) {
        this.globalSettings = globalSettings;
    }

    public void changeScrollPane(Object scrollPane) {
        this.scrollPane = scrollPane;
        changed("changeScrollPane");
    }

    public void changeScrollPane(Function<Boolean,ScrollPane> functionScrollPane) {
        this.functionScrollPane = functionScrollPane;
        changed("changeScrollPane");
    }

    public void cleanScrollPane() {
        changed("cleanScrollPane");
    }

    public Object getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(Object scrollPane) {
        this.scrollPane = scrollPane;
    }

    public Function<Boolean, ScrollPane> getFunctionScrollPane() {
        return functionScrollPane;
    }

    public void setFunctionScrollPane(Function<Boolean, ScrollPane> functionScrollPane) {
        this.functionScrollPane = functionScrollPane;
    }

    public void saveNodeToPdf(Node node, String filePath) {
        System.out.println("To Printer : "+filePath);
        String path =  io.file.Paths.getPathToDirectory(filePath);

        Path p1 = Paths.get(path);
        if (Files.notExists(p1)) {
            try {
                Files.createDirectories(p1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
            addPathToPdfFile(job,"file://"+filePath);
            //           job.getJobSettings();
//            job.showPageSetupDialog( node.getScene().getWindow());
//            job.showPrintDialog(node.getScene().getWindow());
            job.printPage(node);
            job.endJob();
        }
    }

    private void addPathToPdfFile(PrinterJob job, String filePath) {
        try {
            java.lang.reflect.Field field = job.getClass().getDeclaredField("jobImpl");
            field.setAccessible(true);
            PrinterJobImpl jobImpl = (PrinterJobImpl) field.get(job);
            field.setAccessible(false);

            field = jobImpl.getClass().getDeclaredField("printReqAttrSet");
            field.setAccessible(true);
            PrintRequestAttributeSet printReqAttrSet = (PrintRequestAttributeSet) field.get(jobImpl);
            field.setAccessible(false);

            printReqAttrSet.add(new Destination(new java.net.URI(filePath)));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
