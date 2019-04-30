package main;

import designpatterns.ObservableDS;
import experiment.Plan;
import factors.FactorManager;
import math.Combinatorics;
import math.MathP;
import models.MultiParameterModel;
import models.OneParameterModel;
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.util.*;
import java.util.List;
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

    private List<List<String>> multiModelDimensionlessKoefficients;
    private List<List<String>> multiModelDimensionKoefficients;

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



        Random random = new Random(31);
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

    public void calculateCoefficientsMultiParameterModel(List<String> outputFactors, List<String> inputFactors ) {

        multiModelDimensionlessKoefficients = new ArrayList<>();
        multiModelDimensionKoefficients = new ArrayList<>();

        int lengthCell = Integer.parseInt(ProviderSettings.getProjectSettingsMapValue(Settings.Keys.LENGTH_CELL));

        List<String> headerMultiModelDimensionlessKoefficient = new ArrayList<>();
        headerMultiModelDimensionlessKoefficient.add(StringUtil.getStringFormat("0.Model",lengthCell));
        headerMultiModelDimensionlessKoefficient.add(StringUtil.getStringFormat("0.OutputFactors",lengthCell));
        inputFactors.forEach( inputFactorCategoryIdAndName
            ->  headerMultiModelDimensionlessKoefficient.add(StringUtil.getStringFormat(inputFactorCategoryIdAndName,lengthCell))
        );
        multiModelDimensionlessKoefficients.add(headerMultiModelDimensionlessKoefficient);

        List<String> headerMultiModelDimensionKoefficient = new ArrayList<>();
        headerMultiModelDimensionKoefficient.add(StringUtil.getStringFormat("0.Model",lengthCell));
        headerMultiModelDimensionKoefficient.add(StringUtil.getStringFormat("0.OutputFactors",lengthCell));
        headerMultiModelDimensionKoefficient.add(StringUtil.getStringFormat("0.A",lengthCell));
        inputFactors.forEach( inputFactorCategoryIdAndName
            ->  headerMultiModelDimensionKoefficient.add(StringUtil.getStringFormat(inputFactorCategoryIdAndName,lengthCell))
        );
        multiModelDimensionKoefficients.add(headerMultiModelDimensionKoefficient);


        int n = 25;
        List<List<Long>> variantsOfNumber = Combinatorics.getVariants(n, inputFactors.size());

        MultiParameterModel model = new  MultiParameterModel(covarianceCoefficients, characteristicsSeparatedRawDataTable);

        MathP.Counter titleCounter = MathP.getCounter(1,1);

        outputFactors.forEach(
            outputFactorCategoryIdAndName -> {
                variantsOfNumber.forEach(
                    rowOfNumber -> {
                        boolean isCalculated = model.calculateKoefficients(outputFactorCategoryIdAndName, combinatoricsSetFactors(rowOfNumber,inputFactors));

                        if(isCalculated) {
                            String numberOfModel = titleCounter.get().toString();

                            List<String> rowLessKoefficients = new ArrayList<>();
                            fillDefault(headerMultiModelDimensionlessKoefficient, outputFactorCategoryIdAndName, numberOfModel, rowLessKoefficients);
                            fillDimensionLess(headerMultiModelDimensionlessKoefficient, model, rowOfNumber, rowLessKoefficients);
                            multiModelDimensionlessKoefficients.add(rowLessKoefficients);

                            List<String> rowKoefficients = new ArrayList<>();
                            fillDefault(headerMultiModelDimensionKoefficient, outputFactorCategoryIdAndName, numberOfModel, rowKoefficients);
                            fillDimension(headerMultiModelDimensionKoefficient, model, rowOfNumber, rowKoefficients);
                            multiModelDimensionKoefficients.add(rowKoefficients);

                            System.out.println("numberOfModel = "+numberOfModel);

                        }
                    }
                );
            });

        System.out.println("variantsOfNumber = "+variantsOfNumber.size());


    }

    private void fillDimension(List<String> headerMultiModelDimensionKoefficient, MultiParameterModel model, List<Long> rowOfNumber, List<String> rowKoefficients) {

        rowKoefficients.set(2,
            StringUtil.getDoubleFormatValue(
                model.getDimensionKoefficientA(),
                headerMultiModelDimensionKoefficient.get(3).length() - 2
            )
        );

        rowOfNumber.forEach(
            number -> {
                int index = rowOfNumber.indexOf(number);
                rowKoefficients.set(Math.toIntExact(number) + 3,
                    StringUtil.getDoubleFormatValue(
                        model.getDimensionKoefficientsB().get(index),
                        headerMultiModelDimensionKoefficient.get(Math.toIntExact(number) + 2).length() - 2
                    )
                );
            }
        );
    }


    private void fillDimensionLess(List<String> headerMultiModelDimensionKoefficient, MultiParameterModel model, List<Long> rowOfNumber, List<String> rowLessKoefficients) {
        rowOfNumber.forEach(
            number -> {
                int index = rowOfNumber.indexOf(number);
                rowLessKoefficients.set(Math.toIntExact(number) + 2,
                    StringUtil.getDoubleFormatValue(
                        model.getDimensionlessKoefficients().get(index),
                        headerMultiModelDimensionKoefficient.get(Math.toIntExact(number) + 2).length() - 2
                    )
                );
            }
        );
    }

    private void fillDefault(List<String> headerMultiModelDimensionlessKoefficient, String outputFactorCategoryIdAndName, String numberOfModel, List<String> rowLessKoefficients) {
        headerMultiModelDimensionlessKoefficient.forEach(
            headerName -> {
                if (headerMultiModelDimensionlessKoefficient.indexOf(headerName) == 0) {
                    rowLessKoefficients.add(StringUtil.getStringFormat(numberOfModel, headerName.length()-2));
                }
                if (headerMultiModelDimensionlessKoefficient.indexOf(headerName) == 1) {
                    rowLessKoefficients.add(StringUtil.getStringFormat(outputFactorCategoryIdAndName, headerName.length()-2));
                }
                if (headerMultiModelDimensionlessKoefficient.indexOf(headerName) > 1) {
                    rowLessKoefficients.add(StringUtil.getStringFormat("-", headerName.length()-2));
                }
            }
        );
    }

    private List<String> combinatoricsSetFactors (List<Long> rowOfNumber, List<String> inputFactors) {
        List<String> ccmbinatoricsSetFactors = new ArrayList<>();

        rowOfNumber.forEach(
            number -> ccmbinatoricsSetFactors.add(inputFactors.get(Math.toIntExact(number)))
        );
        return  ccmbinatoricsSetFactors;
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

    public void setKoefficientA(List<List<String>> koefficientA) {
        this.koefficientA = koefficientA;
    }

    public void setKoefficientB(List<List<String>> koefficientB) {
        this.koefficientB = koefficientB;
    }

    public List<List<String>> getMultiModelDimensionlessKoefficients() {
        return multiModelDimensionlessKoefficients;
    }

    public void setMultiModelDimensionlessKoefficients(List<List<String>> multiModelDimensionlessKoefficients) {
        this.multiModelDimensionlessKoefficients = multiModelDimensionlessKoefficients;
    }

    public List<List<String>> getMultiModelDimensionKoefficients() {
        return multiModelDimensionKoefficients;
    }

    public void setMultiModelDimensionKoefficients(List<List<String>> multiModelDimensionKoefficients) {
        this.multiModelDimensionKoefficients = multiModelDimensionKoefficients;
    }
}
