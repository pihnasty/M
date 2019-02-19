package main;

import designpatterns.ObservableDS;
import experiment.Plan;
import factors.FactorManager;
import fio.FileUI;
import io.csv.read.CsvReaderP;
import io.csv.write.CsvWriterP;
import io.gson.read.Reader;
import io.gson.write.Writer;
import logging.LoggerP;
import models.OneParameterModel;
import settings.EnumSettings;
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
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


    private List<List<String>> normalizedSeparatedRawDataTable;
    private List<List<String>> covarianceCoefficients;
    private List<List<String>> significanceOfFactors;

    private List<List<String>> characteristicsSeparatedRawDataTable;
    private List<List<String>> characteristicsDimensionlessSeparatedRawDataTable;

    private List<List<String>> koefficientA;
    private List<List<String>> koefficientB;

    private Plan planExperiment = planExperiment = Plan.getInstance();
    private FactorManager factorManager;

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

//        saveData(koefficientA,Settings.Values.ONE_PARAMETER_MODEL_KOEF_A_TABLE_CSV);
//        saveData(koefficientB,Settings.Values.ONE_PARAMETER_MODEL_KOEF_B_TABLE_CSV);

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
        return EnumSettings.readSettingsFromFile(path, fileName);
    }

    public void saveProject() {
        saveProjectSettings();
        saveTableData();
    }

    public void saveAsProject(String pathToProject) {
        getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH, pathToProject.replace("\\","//"));
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




        saveData(koefficientA,Settings.Values.ONE_PARAMETER_MODEL_KOEF_A_TABLE_CSV);
        saveData(koefficientB,Settings.Values.ONE_PARAMETER_MODEL_KOEF_B_TABLE_CSV);

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
        OneParameterModel oneParameterModel = new OneParameterModel();
        oneParameterModel.calculateKoefficientB(covarianceCoefficients, characteristicsSeparatedRawDataTable);
        koefficientA = oneParameterModel.getKoefficientA();
        koefficientB = oneParameterModel.getKoefficientB();
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

    public FactorManager getFactorManager() {
        return factorManager;
    }

    public List<List<String>> getKoefficientA() {
        return koefficientA;
    }

    public List<List<String>> getKoefficientB() {
        return koefficientB;
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
}
