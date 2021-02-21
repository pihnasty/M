package models;

import math.MathP;
import math.linear.SolvingLinearSystems;
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MultiParameterModel {

    private List<List<String>> covarianceСoefficients;
    private List<List<String>> characteristicsSeparatedRawDataTable;
    private List<List<String>> separatedRawDataTable;

    private List<Double> dimensionlessKoefficients;
    private List<Double> koefficientsB;
    private List<String> inputFactors;
    private String outputFactorCategoryIdAndName;
    private List<Integer> indexOfInputFactorsSeparatedRawDataTable = new ArrayList<>();
    private Integer indexOfOutputFactorsSeparatedRawDataTable = -1;
    private double koefficientA;

    private List<String> dimensionLessHeader;
    private List<String> dimensionHeader;
    private List<String> headerMultiModelDimensionlessParameters;
    private List<String> headerMultiModelDimensionParameters;

    private int additionSize = 2;

    private Criterion criterion;

    public MultiParameterModel(List<List<String>> covarianceСoefficients
        , List<List<String>> characteristicsSeparatedRawDataTable, List<List<String>> separatedRawDataTable, List<String> inputFactors) {
        this.covarianceСoefficients = covarianceСoefficients;
        this.characteristicsSeparatedRawDataTable = characteristicsSeparatedRawDataTable;
        this.separatedRawDataTable = separatedRawDataTable;

         criterion = new Criterion(separatedRawDataTable, inputFactors);

    }


    public boolean calculateKoefficients(String outputFactorCategoryIdAndName, List<String> inputFactors) {
        this.inputFactors = inputFactors;
        calculateIndexOfInputFactorsSeparatedRawDataTable(inputFactors);
        this.outputFactorCategoryIdAndName = outputFactorCategoryIdAndName;

        if(!inputFactors.contains(outputFactorCategoryIdAndName)) {
            calculateDimensionlessKoefficients(outputFactorCategoryIdAndName, inputFactors);
            calculateIndexOfOutputFactorsSeparatedRawDataTable( outputFactorCategoryIdAndName);
            calculateDimensionKoefficients(outputFactorCategoryIdAndName, inputFactors);
            return true;
        }
        return false;
    }

    private void calculateIndexOfInputFactorsSeparatedRawDataTable(List<String> inputFactors) {
        indexOfInputFactorsSeparatedRawDataTable = new ArrayList<>();
        List<String> separatedHeaderNameTrim = separatedRawDataTable.get(0).stream().map(String::trim).collect(Collectors.toList());
        inputFactors.stream().forEach(
            nameInputFactor -> this.indexOfInputFactorsSeparatedRawDataTable.add(
                separatedHeaderNameTrim.indexOf(nameInputFactor)
            )
        );
    }

    private void calculateIndexOfOutputFactorsSeparatedRawDataTable(String outputFactorCategoryIdAndName) {
        List<String> separatedHeaderNameTrim = separatedRawDataTable.get(0).stream().map(String::trim).collect(Collectors.toList());
        this.indexOfOutputFactorsSeparatedRawDataTable = separatedHeaderNameTrim.indexOf(outputFactorCategoryIdAndName);
    }

    private void calculateDimensionlessKoefficients(String outputFactorCategoryIdAndName, List<String> inputFactors) {

        List<String> nameCoefficients = covarianceСoefficients.get(0).stream().map(name -> name.trim()).collect(Collectors.toList());

        List<List<Double>> listCoefficients = new ArrayList<>();

        inputFactors.forEach(
            inputFactorCategoryIdAndNameToRow -> {
                List<Double> row = new ArrayList<>();
                inputFactors.forEach(
                    inputFactorCategoryIdAndNameToColumn -> {
                        int inputFactor_1_Number = nameCoefficients.indexOf(inputFactorCategoryIdAndNameToRow);
                        int inputFactor_2_Number = nameCoefficients.indexOf(inputFactorCategoryIdAndNameToColumn);
                        double r_x1_x2 =
                            StringUtil.parseToDouble(
                                covarianceСoefficients.get(inputFactor_1_Number).get(inputFactor_2_Number)
                            );
                        row.add(r_x1_x2);
                    }
                );
                listCoefficients.add(row);
            }
        );


        int outputFactorNumber = nameCoefficients.indexOf(outputFactorCategoryIdAndName);
        List<Double> listfreeTerm = new ArrayList<>();
        inputFactors.forEach(
            inputFactorCategoryIdAndName -> {
                int inputFactorNumber = nameCoefficients.indexOf(inputFactorCategoryIdAndName);
                double r_x_y =
                    StringUtil.parseToDouble(
                        covarianceСoefficients.get(inputFactorNumber).get(outputFactorNumber)
                    );
                listfreeTerm.add(r_x_y);
            }
        );

        SolvingLinearSystems solvingLinearSystems = new SolvingLinearSystems();
        dimensionlessKoefficients = solvingLinearSystems.getSolution(listCoefficients, listfreeTerm);
    }

    private void calculateDimensionKoefficients(String outputFactorCategoryIdAndName, List<String> inputFactors) {

        List<String> nameCoefficients = covarianceСoefficients.get(0).stream().map(name -> name.trim()).collect(Collectors.toList());
        int outputFactorNumber = nameCoefficients.indexOf(outputFactorCategoryIdAndName);

        double standartDeviationY =
            StringUtil.parseToDouble(characteristicsSeparatedRawDataTable.get(2).get(outputFactorNumber));
        koefficientsB = new ArrayList<>();
        dimensionlessKoefficients.forEach(
            beta -> {
                int inputFactorNumber = nameCoefficients.indexOf(
                    inputFactors.get(dimensionlessKoefficients.indexOf(beta)));
                double standartDeviationX =
                    StringUtil.parseToDouble(characteristicsSeparatedRawDataTable.get(2).get(inputFactorNumber));
                koefficientsB.add(beta * standartDeviationY / standartDeviationX);
            }
        );


        double avarageY = StringUtil.parseToDouble(characteristicsSeparatedRawDataTable.get(1).get(outputFactorNumber));
        koefficientA = avarageY;

        for (double b :koefficientsB) {
            int inputFactorNumber = nameCoefficients.indexOf(
                inputFactors.get(koefficientsB.indexOf(b)));
            double avarageX = StringUtil.parseToDouble(characteristicsSeparatedRawDataTable.get(1).get(inputFactorNumber));
            koefficientA -= b * avarageX;
        }

    }


    public List<String> fillDimensionLessHeader (List<String> headerMultiModelDimensionlessParameters, List<String> inputFactors) {
        this.headerMultiModelDimensionlessParameters=headerMultiModelDimensionlessParameters;
        this.dimensionLessHeader = fillHeaderCommon(headerMultiModelDimensionlessParameters, inputFactors);
        return dimensionLessHeader;
    }

    public List<String> fillDimensionHeader (List<String> headerMultiModelDimensionParameters, List<String> inputFactors) {
        this.headerMultiModelDimensionParameters=headerMultiModelDimensionParameters;
        this.dimensionHeader = fillHeaderCommon(headerMultiModelDimensionParameters, inputFactors);
        return dimensionHeader;
    }

    public List<String> fillHeaderCommon(List<String> headerMultiModelDimensionlessParameters, List<String> inputFactors) {
        int lengthCell = Integer.parseInt(ProviderSettings.getProjectSettingsMapValue(Settings.Keys.LENGTH_CELL));
        List<String> dimensionLessHeader = new ArrayList<>();
        headerMultiModelDimensionlessParameters.forEach(
            parameter -> dimensionLessHeader.add(StringUtil.getStringFormat(parameter, lengthCell))
        );
        inputFactors.forEach(inputFactorCategoryIdAndName
            -> dimensionLessHeader.add(StringUtil.getStringFormat(inputFactorCategoryIdAndName, lengthCell))
        );
        return dimensionLessHeader;
    }

    public void fillDefaultDimensionLess( String outputFactorCategoryIdAndName, String numberOfModel, List<String> rowLessKoefficients) {
        fillDefault(outputFactorCategoryIdAndName, numberOfModel, rowLessKoefficients, dimensionLessHeader);
    }

    public void fillDefaultDimension( String outputFactorCategoryIdAndName, String numberOfModel, List<String> rowKoefficients) {
        fillDefault(outputFactorCategoryIdAndName, numberOfModel, rowKoefficients, dimensionHeader);
    }

    private void fillDefault(String outputFactorCategoryIdAndName, String numberOfModel, List<String> rowKoefficients, List<String> dimensionHeader) {
        dimensionHeader.forEach(
            headerName -> {
                if (dimensionHeader.indexOf(headerName) == 0) {
                    rowKoefficients.add(StringUtil.getStringFormat(numberOfModel, headerName.length()-additionSize));
                }
                if (dimensionHeader.indexOf(headerName) == 1) {
                    rowKoefficients.add(StringUtil.getStringFormat(outputFactorCategoryIdAndName, headerName.length()-additionSize));
                }
                if (dimensionHeader.indexOf(headerName) > 1) {
                    rowKoefficients.add(StringUtil.getStringFormat("-", headerName.length()-additionSize));
                }
            }
        );
    }

    public void fillDimensionLess(List<Long> rowOfNumber, List<String> rowLessKoefficients) {
        rowOfNumber.forEach(
            number -> {
                int index = rowOfNumber.indexOf(number);
                rowLessKoefficients.set(Math.toIntExact(number) + headerMultiModelDimensionlessParameters.size(),
                    StringUtil.getDoubleFormatValue(
                        getDimensionlessKoefficients().get(index),
                        dimensionLessHeader.get(Math.toIntExact(number)
                            + headerMultiModelDimensionlessParameters.size()
                        ).length() - additionSize
                    )
                );
            }
        );
    }

    public  void fillDimension(List<Long> rowOfNumber, List<String> rowKoefficients, List<List<String>> residualsTable) {

        double SSE = 0.0;
        double sumResidual = 0.0;
        Double countRow = 0.0;
        Random random = new Random(31);
        double numberKoefficientsInModel = rowOfNumber.size()+1;

        Map<Double, List<String>> sortedResidual = new TreeMap<>();

        for (List<String> row : separatedRawDataTable) {

            if (separatedRawDataTable.indexOf(row)>0) {
                String stringOutputValue = row.get(indexOfOutputFactorsSeparatedRawDataTable).trim();
                if(!stringOutputValue.equalsIgnoreCase("-")) {

                    double yOutputFactor = StringUtil.parseToDouble(row.get(indexOfOutputFactorsSeparatedRawDataTable));
                    List<Double> values = new ArrayList<>();
                    double yPrediction = koefficientA;

                    boolean isCellEmpty = false;

                    for (int i = 0; i < koefficientsB.size(); i++) {
                        int numberInputValue =  indexOfInputFactorsSeparatedRawDataTable.get(i);

                        isCellEmpty =  row.get(numberInputValue).trim().equalsIgnoreCase("-");
                        double inputValue;
                        if (isCellEmpty) {
                            break;
                        } else {
                            inputValue = StringUtil.parseToDouble(row.get(numberInputValue));
                        }

//                        double inputValue = row.get(
//                            numberInputValue
//                        ).trim().equalsIgnoreCase("-") ? 0.0 : StringUtil.parseToDouble(row.get(numberInputValue));

                        values.add(inputValue);
                        yPrediction += koefficientsB.get(i) * inputValue;
                    }

                    if (isCellEmpty) {
                        continue;
                    }

                    Double residual = yOutputFactor-yPrediction;  ;
                    List<String> residualPropertyList = new ArrayList<>();
                    residualPropertyList.add(row.get(0));
                    residualPropertyList.add(Double.toString(yOutputFactor));
                    residualPropertyList.add(Double.toString(yPrediction));

                    if (sortedResidual.containsKey(residual)) {
                        residual += random.nextDouble() / 10000.0;
                        sortedResidual.put(residual, residualPropertyList);
                    } else {
                        sortedResidual.put(residual, residualPropertyList);
                    }

                    sumResidual += residual;
                    SSE += residual*residual;
                    countRow += 1.0;

                }

            }
        }


        rowKoefficients.set(2,
            StringUtil.getDoubleFormatValue(
                countRow,
                dimensionHeader.get(2).length() - additionSize
            )
        );

        rowKoefficients.set(3,
            StringUtil.getDoubleFormatValue(
                //criterion.get( Settings.Values.NUMBER_CONSTRAINTS),
                numberKoefficientsInModel,
                dimensionHeader.get(3).length() - additionSize
            )
        );

 CopyOnWriteArrayList c;

        double meanResidual = sumResidual/countRow;
        rowKoefficients.set(4,
            StringUtil.getDoubleFormatValue(
                meanResidual,
                dimensionHeader.get(4).length() - additionSize
            )
        );

        rowKoefficients.set(5,
            StringUtil.getDoubleFormatValue(
                SSE,
                dimensionHeader.get(5).length() - additionSize
            )
        );

        double MSE = SSE/( countRow- numberKoefficientsInModel );
        rowKoefficients.set(6,
            StringUtil.getDoubleFormatValue(
                MSE,
                dimensionHeader.get(6).length() - additionSize
            )
        );

        double standardDeviation =  Math.sqrt(MSE);
        rowKoefficients.set(7,
            StringUtil.getDoubleFormatValue(
                standardDeviation,
                dimensionHeader.get(7).length() - additionSize
            )
        );


        rowKoefficients.set(headerMultiModelDimensionParameters.size()-1,
            StringUtil.getDoubleFormatValue(
               getKoefficientA(),
                dimensionHeader.get(headerMultiModelDimensionParameters.size()-1).length() - additionSize
            )
        );

        rowOfNumber.forEach(
            number -> {
                int index = rowOfNumber.indexOf(number);
                rowKoefficients.set(Math.toIntExact(number) + headerMultiModelDimensionParameters.size(),
                    StringUtil.getDoubleFormatValue(
                        getKoefficientsB().get(index),
                        dimensionHeader.get(Math.toIntExact(number) + headerMultiModelDimensionParameters.size()-1).length() - additionSize
                    )
                );
            }
        );

        fillResidealTable(residualsTable, sortedResidual, meanResidual, standardDeviation);
    }

    private void fillResidealTable(List<List<String>> residualsTable, Map<Double, List<String>> sortedResidual, double meanResidual, double standardDeviation) {
        TreeMap<Double, Double> treeMapCash = new TreeMap<>();
        MathP.Counter counter = MathP.getCounter(1,1);
        int numberMax = sortedResidual.size();
        Double mean = meanResidual;
        sortedResidual.forEach(
            (key, value) -> {
                List<String> row = new ArrayList<>();

                List<String> residualPropertyList = value;

                row.add(StringUtil.getStringFormat(
                    residualPropertyList.get(0)
                    ,residualsTable.get(0).get(0).length() - additionSize));

                row.add(StringUtil.getDoubleFormatValue(key
                    ,residualsTable.get(0).get(1).length() - additionSize));

                row.add(StringUtil.getDoubleFormatValue(
                    mean
                    ,residualsTable.get(0).get(2).length() - additionSize));

                row.add(StringUtil.getDoubleFormatValue(
                    standardDeviation
                    ,residualsTable.get(0).get(3).length() - additionSize));

                int count = counter.get();
                double cumulativeProbabilityValue = MathP.getCumulativeProbabilityValue(count, numberMax);


                row.add(StringUtil.getDoubleFormatValue(
                    (double) count
                    ,residualsTable.get(0).get(4).length() - additionSize));

                row.add(StringUtil.getDoubleFormatValue(
                    cumulativeProbabilityValue
                    ,residualsTable.get(0).get(5).length() - additionSize));

                double zValue = MathP.zValue(cumulativeProbabilityValue, treeMapCash, 0.001);
                row.add(StringUtil.getDoubleFormatValue(
                    zValue
                    ,residualsTable.get(0).get(6).length() - additionSize));

                row.add(StringUtil.getDoubleFormatValue(
                    zValue * standardDeviation
                    ,residualsTable.get(0).get(7).length() - additionSize));

                row.add(StringUtil.getStringFormat(
                    residualPropertyList.get(1)
                    ,residualsTable.get(0).get(8).length() - additionSize));

                row.add(StringUtil.getDoubleFormatValue(
                    Double.parseDouble(residualPropertyList.get(2))
                    ,residualsTable.get(0).get(8).length() - additionSize));

                residualsTable.add(row);
            }
        );
    }

    public List<Double> getDimensionlessKoefficients() {
        return dimensionlessKoefficients;
    }

    public List<Double> getKoefficientsB() {
        return koefficientsB;
    }

    public double getKoefficientA() {
        return koefficientA;
    }

    public List<String> getDimensionLessHeader() {
        return dimensionLessHeader;
    }

    public List<String> getDimensionHeader() {
        return dimensionHeader;
    }
}
