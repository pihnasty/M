package main;

import com.sun.javafx.print.PrinterJobImpl;
import designpatterns.ObservableDS;
import experiment.Plan;
import fio.FileUI;
import io.csv.read.CsvReaderP;
import io.csv.write.CsvWriterP;
import io.gson.read.Reader;
import io.gson.write.Writer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import logging.LoggerP;
import logging.LoggerP_test;
import neural.network.NeuralManager;
import neural.network.NeuralModel;
import neural.network.layers.Layer;
import neural.network.ws.Ws;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
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

    private Plan planExperiment = Plan.getInstance();

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
        getGlobalSettings().getMap().put(Settings.Keys.PROJECT_PATH, pathToProject.replace("\\","//"));
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

        project.getResidualsTables().keySet().forEach(
            key -> saveData(project.getResidualsTables().get(key), String.format(Settings.Values.MULTI_MODEL_RESIDUAL_CSV, key.trim()))
        );

}
    public void deleteFile(String fileName) {
        String path = io.file.Paths.getPathToDirectory(getProjectPath()+"//"+fileName);
        fileName = io.file.Paths.getShortFileName(getProjectPath()+"//"+fileName);
        String relativePathForDeleteFile = path+"//"+fileName;
        if(new File(relativePathForDeleteFile).delete()) {
            LoggerP.write(Level.INFO, "file " + relativePathForDeleteFile + " was deleted.");
        }

    }

    public void saveData(List<List<String>> dataTable, String fileName) {
        String path = io.file.Paths.getPathToDirectory(getProjectPath()+"//"+fileName);
        fileName = io.file.Paths.getShortFileName(getProjectPath()+"//"+fileName);
        saveData(dataTable, fileName, path);
    }

    public void saveData(List<List<String>> dataTable, String fileName, String path) {
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

    public void continueLearningNeuralNet() {
        String pathDeserialize = getProjectPath() + "//" + Settings.Values.NEURAL_NETWORk_MODEL
            + "//" + Settings.Values.LEARNING_ANALYSIS_TOOL
            + "//" + Settings.Values.WS_SERIALIZE;


        String lastFileName = "";
        int lastSaveNumberEpoch = 1;

        List<String> fileNames = io.file.Paths.sortedFileNameFromDirectory(pathDeserialize, Settings.Values.LEARNING_WORD);

        if (Objects.nonNull(fileNames) && !fileNames.isEmpty()) {
            for (int i = fileNames.size() - 1; i >= 0; i--) {
                lastFileName = fileNames.get(i);
                try {
                    lastSaveNumberEpoch = Integer.parseInt(lastFileName.replace(Settings.Values.LEARNING_WORD, ""));
                } catch (NumberFormatException e) {
                    continue;
                }
                break;
            }

        }

        String statFullFileName = getProjectPath() + "//" + Settings.Values.NEURAL_NETWORk_MODEL
            + "//" + Settings.Values.LEARNING_ANALYSIS_TOOL
            + "//" + Settings.Values.STATISTICS+"//"+lastSaveNumberEpoch+Settings.Values.ERROR;

        uploadErrorsStatNeuralNet(statFullFileName);
        deserializeNeuralNet(pathDeserialize, lastFileName);
        learningNeuralNet(lastSaveNumberEpoch+1, false );
    }


    public void deserializeNeuralNet(String path, String  fileName ) {
        project.setWsS(NeuralManager.getManager().deserializeNeuralNet(path,fileName));
    }

    public void learningNeuralNet(int startWithEpoch, boolean isDeletedPastResultLearninOfAnalysisTool) {
        Map<String, String> parametersOfNeuralNetworkModel = getPlanExperiment().getParametersOfNeuralNetworkModel();
        int numberOfEpochs = Integer.parseInt(parametersOfNeuralNetworkModel.get(Settings.Keys.NUMBER_OF_EPOCHS ));
        int numberOfEpochsBetweenSave = Integer.parseInt(parametersOfNeuralNetworkModel.get(Settings.Keys.NUMBER_OF_EPOCHS_BETWEEN_SAVE));
        int cpuCoolingTimeSeconds = Integer.parseInt(parametersOfNeuralNetworkModel.get(Settings.Keys.CPU_COOLING_TIME_SECONDS));
        int numberOfEpochsBetweenCpuCooling = Integer.parseInt(parametersOfNeuralNetworkModel.get(Settings.Keys.NUMBER_OF_EPOCHS_BETWEEN_CPU_COOLING));
        int batchSize = Integer.parseInt(parametersOfNeuralNetworkModel.get(Settings.Keys.BATCH_SIZE));

        NeuralModel neuralModel = new NeuralModel();
        NeuralManager neuralManager = NeuralManager.getManager();
        neuralManager.setNeuralModel(neuralModel);
        neuralManager.setBatchSize(batchSize);

        List<String> inputFactors = getPlanExperiment().getInputFactors();
        List<String> outputFactors = getPlanExperiment().getOutputFactors();
        List<List<String>> separatedRawDataTable = project.getSeparatedRawDataTable();
        neuralManager.buildArchitecture(inputFactors, getPlanExperiment().getHiddenLayers(), outputFactors);
        if (isDeletedPastResultLearninOfAnalysisTool) {
            neuralManager.randomInitWs();
        } else {
            List<Ws> listWs = project.getWsS();
            List<Layer> layers = neuralModel.getLayers();
            for (int i = 1; i < layers.size(); i++) {
                layers.get(i).setW(listWs.get(i));
            }
        }

        neuralManager.prepareForLearningTable(inputFactors, outputFactors, separatedRawDataTable);

        List<List<String>> errorsStat = neuralManager.getErrorsStat();

        long startTime = System.currentTimeMillis()/1000;

        for (int i = startWithEpoch; i <numberOfEpochs; i++) {

            Double MSE = neuralManager.learningNeuralNet();

            if (i == startWithEpoch) {
                if (errorsStat.isEmpty()) {
                    List<String> header = new ArrayList<>();
                    header.add("        epoch      ");
                    header.add("  log10(epoch)     ");
                    header.add("           MSE           ");
                    header.add("         delta MSE       ");
                    header.add("  time(sec) ");
                    errorsStat.add(header);
                }
            }

            long currentTimeTime = System.currentTimeMillis()/1000;
            long deltaTime = (currentTimeTime-startTime);
            long minute = deltaTime /60;
            long sec = deltaTime % 60;

            List<String> rowErrorsStat = new ArrayList<>();
            rowErrorsStat.add(StringUtil.getDoubleFormatValue((double)i,errorsStat.get(0).get(0),".0f",1));
            rowErrorsStat.add(StringUtil.getDoubleFormatValue(Math.log10((double)i),errorsStat.get(0).get(1),".4f",1));
            rowErrorsStat.add(StringUtil.getDoubleFormatValue(MSE,errorsStat.get(0).get(2),".14f",1));
            double deltaMSE = MSE
                - ( errorsStat.size()==1 ? 0.0 : Double.parseDouble(errorsStat.get(errorsStat.size()-1).get(2).replace(",",".")) );
            rowErrorsStat.add(StringUtil.getDoubleFormatValue(deltaMSE,errorsStat.get(0).get(3),".14f",1));
            rowErrorsStat.add(StringUtil.getDoubleFormatValue((double)deltaTime,errorsStat.get(0).get(4),".0f",1));
            errorsStat.add(rowErrorsStat);

            project.setWsS(neuralModel.getLayers().stream().map(layer -> layer.getW()).collect(Collectors.toList()));
            if (i % numberOfEpochsBetweenCpuCooling == 0) {
                try {
                    LoggerP.logger.log(Level.INFO,String.format("Остановлен расчет на %d сек для снижения температуры процессора", cpuCoolingTimeSeconds));
                    Thread.sleep(cpuCoolingTimeSeconds * 1000);
                } catch (InterruptedException e) {
                    // nothing
                }
            }
            if (i % numberOfEpochsBetweenSave == 0) {
                if(isDeletedPastResultLearninOfAnalysisTool) {
                    String pathS = getProjectPath()
                        + "//" + Settings.Values.NEURAL_NETWORk_MODEL + "//" + Settings.Values.LEARNING_ANALYSIS_TOOL ;
                    io.file.Paths.deleteDirectory(pathS);
                    isDeletedPastResultLearninOfAnalysisTool = false;
                }
                serializeNeuralNet(i );
                runDataAnalysisNeuralNet(i);
                saveErrorsStat(i, errorsStat, numberOfEpochsBetweenSave);
            }
            if (i > 100000) {
                if (i % 20 == 0) {
                    LoggerP_test.logger.setLevel(Level.INFO);
                    LoggerP_test.write(Level.INFO, "--------------------------------------------------------------------------------------------------------------------");
                    LoggerP_test.write(Level.INFO, LocalDateTime.now() + "  " + String.format("%8d-- ln= %5.3f  MSE=%.14f  deltaMSE=%.14f", i, Math.log10(i), MSE, deltaMSE));
                }
            }

           if (i % 10 == 0) {
                System.out.println(LocalDateTime.now() + "  "+String.format("%8d-- ln= %5.3f  MSE=%.14f  deltaMSE=%.14f time %d : %d", i, Math.log10(i), MSE, deltaMSE, minute, sec));
            }
        }

    }

    public void serializeNeuralNet() {
        String path = io.file.Paths.getPathToDirectory(getProjectPath() + "//" + Settings.Values.NEURAL_NETWORk_MODEL_WS_SERIALIZE);
        String fileName = io.file.Paths.getShortFileName(getProjectPath() + "//" + Settings.Values.NEURAL_NETWORk_MODEL_WS_SERIALIZE);
        NeuralManager.getManager().serializeNeuralNet(project.getWsS(), path,fileName);
    }

    public void serializeNeuralNet(int numberEpochs ) {
        String relativePath = Settings.Values.NEURAL_NETWORk_MODEL
            + "//" + Settings.Values.LEARNING_ANALYSIS_TOOL
            + "//" + Settings.Values.WS_SERIALIZE + String.format("//%s%010d",Settings.Values.LEARNING_WORD,numberEpochs);
        String path = io.file.Paths.getPathToDirectory(getProjectPath() + "//" + relativePath);
        String fileName = io.file.Paths.getShortFileName(getProjectPath() + "//" + relativePath);
        NeuralManager.getManager().serializeNeuralNet(project.getWsS(), path,fileName);
    }

    public void deserializeNeuralNet() {
        String path = io.file.Paths.getPathToDirectory(getProjectPath()+"//"+Settings.Values.NEURAL_NETWORk_MODEL_WS_SERIALIZE);
        String  fileName = io.file.Paths.getShortFileName(getProjectPath()+"//"+Settings.Values.NEURAL_NETWORk_MODEL_WS_SERIALIZE);
        project.setWsS(NeuralManager.getManager().deserializeNeuralNet(path,fileName));
    }

    public void runDataAnalysisNeuralNet() {

        NeuralModel neuralModel = new NeuralModel();
        NeuralManager neuralManager = NeuralManager.getManager();
        neuralManager.setNeuralModel(neuralModel);

        List<String> inputFactors = getPlanExperiment().getInputFactors();
        List<String> outputFactors = getPlanExperiment().getOutputFactors();

        List<List<String>> dataTableForAnalysis = project.getDataTableForAnalysisNeuralNet();

        neuralManager.buildArchitecture(
            inputFactors,
            getPlanExperiment().getHiddenLayers(),
            outputFactors
        );

        neuralManager.useDeserializeWs(project.getWsS());
        neuralManager.prepareForLearningTable(inputFactors, outputFactors, dataTableForAnalysis);
        neuralManager.predictionNeuralNet();

        List<List<String>> summaryData = new ArrayList<>();
        for (int i=0; i<dataTableForAnalysis.size(); i++) {
            List<String> row = new ArrayList<>();
            row.addAll(dataTableForAnalysis.get(i));
            row.addAll(neuralManager.getDataTableAfterAnalysisNeuralNet().get(i));
            summaryData.add(row);
        }
        project.setDataTableAfterAnalysisNeuralNet(summaryData);
    }

    public void runDataAnalysisNeuralNet(int numberEpochs) {
        project.setDataTableForAnalysisNeuralNet(project.getSeparatedRawDataTable());
        runDataAnalysisNeuralNet();
        String relativePath = Settings.Values.NEURAL_NETWORk_MODEL
            + "//" + Settings.Values.LEARNING_ANALYSIS_TOOL
            + "//" + Settings.Values.TESTING_RUN + String.format("//%s%010d.csv",Settings.Values.LEARNING_WORD, numberEpochs);
        saveData(project.getDataTableAfterAnalysisNeuralNet(), relativePath);
    }

    public void saveErrorsStat(int i, List<List<String>> errorsStat, int numberOfEpochsBetweenSave) {
        project.setDataTableForAnalysisNeuralNet(project.getSeparatedRawDataTable());
        runDataAnalysisNeuralNet();
        String relativePath = Settings.Values.NEURAL_NETWORk_MODEL
            + "//" + Settings.Values.LEARNING_ANALYSIS_TOOL
            + "//" + Settings.Values.STATISTICS + "//" + i+Settings.Values.ERROR;

        String relativePathForDeleteFile = Settings.Values.NEURAL_NETWORk_MODEL
            + "//" + Settings.Values.LEARNING_ANALYSIS_TOOL
            + "//" + Settings.Values.STATISTICS + "//" + (i-2*numberOfEpochsBetweenSave) +Settings.Values.ERROR;

        deleteFile(relativePathForDeleteFile);
        saveData(getUniqueErrorsStat(errorsStat), relativePath);
    }

    private List<List<String>> getUniqueErrorsStat(List<List<String>> errorsStat) {
        List<List<String>> uniqueErrorsStat = new ArrayList<>();
        for (int k=0; k<errorsStat.size(); k++) {
            List<String> currentRow = errorsStat.get(k);
            if (k < 100) {
                uniqueErrorsStat.add(currentRow);
            } else {
                List<String> prevCurrentRow = errorsStat.get(k - 1);
                if (!currentRow.get(1).equals(prevCurrentRow.get(1))) {
                    uniqueErrorsStat.add(currentRow);
                }
            }
        }
        return uniqueErrorsStat;
    }


    public void saveDataAnalysisNeuralNet() {
        saveData(project.getDataTableAfterAnalysisNeuralNet(),Settings.Values.DATA_TABLE_AFTER_ANALYSIS_CSV );
    }

    public void uploadDataAnalysisNeuralNet(String fullFileName) {
        project.setDataTableForAnalysisNeuralNet(readTableFromFile(fullFileName));
    }

    public void uploadErrorsStatNeuralNet(String fullFileName) {
        NeuralManager neuralManager = NeuralManager.getManager();
        neuralManager.setErrorsStat(readTableFromFile(fullFileName));
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
              job.getJobSettings();
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
