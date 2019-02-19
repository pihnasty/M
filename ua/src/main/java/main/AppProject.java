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
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class AppProject extends ObservableDS {

    private static AppProject ourInstance = new AppProject();

    public static AppProject getInstance() {
        return ourInstance;
    }

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

    private FactorManager factorManager;

    private AppProject() {

    }

    public boolean separatedRawData(Settings projectSettings, Settings defaultSettings) {
//        if (Objects.isNull(rawDataTable) || rawDataTable.isEmpty()) {
//            return false;
//        }

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

    public void fastStart() {

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

    public List<List<String>> getRawDataTable() {
        return rawDataTable;
    }

    public void setRawDataTable(List<List<String>> rawDataTable) {
        this.rawDataTable = rawDataTable;
    }

    public List<List<String>> getNameCategoryTable() {
        return nameCategoryTable;
    }

    public void setNameCategoryTable(List<List<String>> nameCategoryTable) {
        this.nameCategoryTable = nameCategoryTable;
    }

    public List<List<String>> getSeparatedRawDataTable() {
        return separatedRawDataTable;
    }

    public void setSeparatedRawDataTable(List<List<String>> separatedRawDataTable) {
        this.separatedRawDataTable = separatedRawDataTable;
    }

    public List<List<String>> getTestedRawDataTable() {
        return testedRawDataTable;
    }

    public void setTestedRawDataTable(List<List<String>> testedRawDataTable) {
        this.testedRawDataTable = testedRawDataTable;
    }

    public List<List<String>> getNormalizedSeparatedRawDataTable() {
        return normalizedSeparatedRawDataTable;
    }

    public void setNormalizedSeparatedRawDataTable(List<List<String>> normalizedSeparatedRawDataTable) {
        this.normalizedSeparatedRawDataTable = normalizedSeparatedRawDataTable;
    }

    public List<List<String>> getCovarianceCoefficients() {
        return covarianceCoefficients;
    }

    public void setCovarianceCoefficients(List<List<String>> covarianceCoefficients) {
        this.covarianceCoefficients = covarianceCoefficients;
    }

    public List<List<String>> getSignificanceOfFactors() {
        return significanceOfFactors;
    }

    public void setSignificanceOfFactors(List<List<String>> significanceOfFactors) {
        this.significanceOfFactors = significanceOfFactors;
    }

    public List<List<String>> getCharacteristicsSeparatedRawDataTable() {
        return characteristicsSeparatedRawDataTable;
    }

    public void setCharacteristicsSeparatedRawDataTable(List<List<String>> characteristicsSeparatedRawDataTable) {
        this.characteristicsSeparatedRawDataTable = characteristicsSeparatedRawDataTable;
    }

    public List<List<String>> getCharacteristicsDimensionlessSeparatedRawDataTable() {
        return characteristicsDimensionlessSeparatedRawDataTable;
    }

    public void setCharacteristicsDimensionlessSeparatedRawDataTable(List<List<String>> characteristicsDimensionlessSeparatedRawDataTable) {
        this.characteristicsDimensionlessSeparatedRawDataTable = characteristicsDimensionlessSeparatedRawDataTable;
    }
}
