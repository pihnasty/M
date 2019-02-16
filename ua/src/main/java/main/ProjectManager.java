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
    private Settings globalSettings = ProviderSettings.getSettings(EnumSettings.PROJECT);

    private List<List<String>> rawDataTable;
    private List<List<String>> nameCategoryTable;
    private List<List<String>> separatedRawDataTable;
    private List<List<String>> testedRawDataTable;
    private List<List<String>> normalizedSeparatedRawDataTable;
    private List<List<String>> covarianceCoefficients;
    private List<List<String>> significanceOfFactors;

    private List<List<String>> characteristicsSeparatedRawDataTable;
    private List<List<String>> characteristicsDimensionlessSeparatedRawDataTable;

    private List<List<String>> koefficientA;
    private List<List<String>> koefficientB;

    private Plan planExperiment;
    private FactorManager factorManager;

    private ProjectManager() {

    }

    public void openProject(String pathToProject) {
        LoggerP.logger.log(Level.SEVERE, "openProject", "LoggerP");
        getDefaultSettings().getMap().put(Settings.Keys.PROJECT_PATH,pathToProject);
    }

    public void saveProject() {
        saveProjectSettings();
        saveRawData();
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

    public List<List<String>> getRawDataTable() {
        return rawDataTable;
    }

    public void setRawDataTable(List<List<String>> rawDataTable) {
        this.rawDataTable = rawDataTable;
    }

    public void uploadRawData(String fullFileName) {
        rawDataTable = readTableFromFile(fullFileName);
    }

    public void uploadNameCategory(String fullFileName) {
        nameCategoryTable = readTableFromFile(fullFileName);
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

    public void saveRawData() {
        saveData(rawDataTable,Settings.Values.RAW_DATA_TABLE_CSV );
        saveData(nameCategoryTable,Settings.Values.NAME_CATEGORY_TABLE_CSV);
        saveData(separatedRawDataTable,Settings.Values.SEPARATED_RAW_DATA_TABLE_CSV );
        saveData(testedRawDataTable,Settings.Values.TESTED_RAW_DATA_TABLE_CSV );
        saveData(normalizedSeparatedRawDataTable,Settings.Values.NORMALIZED_SEPARATED_RAW_DATA_TABLE_CSV);
        saveData(covarianceCoefficients,Settings.Values.COVARIANCE_COEFFICIENTS_SEPARATED_RAW_DATA_TABLE_CSV);
        saveData(significanceOfFactors,Settings.Values.SIGNIFICANCE_FACTORS_SEPARATED_RAW_DATA_TABLE_CSV);

        saveData(characteristicsSeparatedRawDataTable,Settings.Values.CHARACTERISTICS_SEPARATED_RAW_DATA_TABLE_CSV);
        saveData(characteristicsDimensionlessSeparatedRawDataTable,Settings.Values.CHARACTERISTICS_DIMENSIONLESS_SEPARATED_RAW_DATA_TABLE_CSV);

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

    public boolean separatedRawData() {
        if (Objects.isNull(rawDataTable) || rawDataTable.isEmpty()) {
            return false;
        }

        Double partOfTestData = Objects.isNull(projectSettings.getMap().get(Settings.Keys.PART_OF_THE_TEST_DATA))
            ? Double.parseDouble(defaultSettings.getMap().get(Settings.Keys.DEFAULT_PART_OF_THE_TEST_DATA))
            : Double.parseDouble(projectSettings.getMap().get(Settings.Keys.PART_OF_THE_TEST_DATA));
        projectSettings.getMap().put(Settings.Keys.PART_OF_THE_TEST_DATA, partOfTestData.toString() );



        Random random = new Random();
        List<List<String>> tempRawDataTable = new ArrayList<>(rawDataTable);
        List<String> headerRow = tempRawDataTable.remove(0);
        int testSeparetedSize = (int) ((tempRawDataTable.size())*partOfTestData);


        separatedRawDataTable = new ArrayList<>();
        Map<Integer,List<String>> mapTestSeparatedRawDataTable = new HashMap<>();

        while (mapTestSeparatedRawDataTable.size() <= testSeparetedSize ) {
            int randomNumber = random.nextInt(rawDataTable.size()-1);
            List<String> row =tempRawDataTable.get(randomNumber);

            if (!mapTestSeparatedRawDataTable.containsKey(randomNumber)) {
                mapTestSeparatedRawDataTable.put(randomNumber,row);
            }
        }

        List<Integer> testedKeys =  mapTestSeparatedRawDataTable.keySet().stream().sorted().collect(Collectors.toList());



        testedRawDataTable = new ArrayList<>();
        testedRawDataTable.add(headerRow);
        testedKeys.forEach(number -> testedRawDataTable.add(tempRawDataTable.get(number)));

        separatedRawDataTable = new ArrayList<>(rawDataTable);
        separatedRawDataTable.removeAll(testedRawDataTable);
        separatedRawDataTable.add(0,headerRow);

        return true;

    }

    public void createFactors() {

        factorManager = new FactorManager(separatedRawDataTable);
        normalizedSeparatedRawDataTable=factorManager.getNormalazeSeparatedRawDataTable();
        covarianceCoefficients =factorManager.getCovarianceCoefficients();
        significanceOfFactors = factorManager.getSignificanceOfFactors();
        characteristicsSeparatedRawDataTable =factorManager.getCharacteristicsSeparatedRawDataTable();
        characteristicsDimensionlessSeparatedRawDataTable =factorManager.getCharacteristicsDimensionlessSeparatedRawDataTable();

    }

    public void calculateCoefficientsOneParameterModel_a_b() {
        OneParameterModel oneParameterModel = new OneParameterModel();
        oneParameterModel.calculateKoefficientB(covarianceCoefficients, characteristicsSeparatedRawDataTable);
        koefficientA = oneParameterModel.getKoefficientA();
        koefficientB = oneParameterModel.getKoefficientB();
    }

    public void downloadExperimentPlan(String fullFileName) {
        planExperiment = Plan.getInstance();
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
}
