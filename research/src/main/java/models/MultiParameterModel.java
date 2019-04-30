package models;

import math.linear.SolvingLinearSystems;
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiParameterModel {

    private List<List<String>> covarianceСoefficients;
    private List<List<String>> characteristicsSeparatedRawDataTable;

    private List<Double> dimensionlessKoefficients;
    private List<Double> dimensionKoefficientsB;
    private double dimensionKoefficientA;


    public MultiParameterModel(List<List<String>> covarianceСoefficients
        , List<List<String>> characteristicsSeparatedRawDataTable) {
        this.covarianceСoefficients = covarianceСoefficients ;
        this.characteristicsSeparatedRawDataTable = characteristicsSeparatedRawDataTable;
    }


    public boolean calculateKoefficients(String outputFactorCategoryIdAndName, List<String> inputFactors) {
        if(!inputFactors.contains(outputFactorCategoryIdAndName)) {
            calculateDimensionlessKoefficients(outputFactorCategoryIdAndName, inputFactors);
            calculateDimensionKoefficients(outputFactorCategoryIdAndName, inputFactors);
            return true;
        }
        return false;
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
        dimensionKoefficientsB = new ArrayList<>();
        dimensionlessKoefficients.forEach(
            beta -> {
                int inputFactorNumber = nameCoefficients.indexOf(
                    inputFactors.get(dimensionlessKoefficients.indexOf(beta)));
                double standartDeviationX =
                    StringUtil.parseToDouble(characteristicsSeparatedRawDataTable.get(2).get(inputFactorNumber));
                dimensionKoefficientsB.add(beta * standartDeviationY / standartDeviationX);
            }
        );


        double avarageY = StringUtil.parseToDouble(characteristicsSeparatedRawDataTable.get(1).get(outputFactorNumber));
        dimensionKoefficientA = avarageY;

        for (double b : dimensionKoefficientsB) {
            int inputFactorNumber = nameCoefficients.indexOf(
                inputFactors.get(dimensionKoefficientsB.indexOf(b)));
            double avarageX = StringUtil.parseToDouble(characteristicsSeparatedRawDataTable.get(1).get(inputFactorNumber));
            dimensionKoefficientA -= b * avarageX;
        }

    }

    public List<Double> getDimensionlessKoefficients() {
        return dimensionlessKoefficients;
    }

    public List<Double> getDimensionKoefficientsB() {
        return dimensionKoefficientsB;
    }

    public double getDimensionKoefficientA() {
        return dimensionKoefficientA;
    }
}
