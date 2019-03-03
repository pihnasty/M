package models;

import factors.Factor;

import java.util.List;
import java.util.stream.Collectors;

public class TwoParameterModel {

    private List<List<String>> covarianceСoefficients;
    private List<List<String>> characteristicsSeparatedRawDataTable;


    private double koefficientBeta1;
    private double koefficientBeta2;

    private double koefficientA;
    private double koefficientB1;
    private double koefficientB2;

    private double standartDeviationY;


    public TwoParameterModel(List<List<String>> covarianceСoefficients, List<List<String>> characteristicsSeparatedRawDataTable) {
        this.covarianceСoefficients = covarianceСoefficients;
        this.characteristicsSeparatedRawDataTable = characteristicsSeparatedRawDataTable;
    }

    public void calculateKoefficientsBeta(Factor outputFactor, Factor inputFactor1, Factor inputFactor2) {
        List<String> nameCoefficients = covarianceСoefficients.get(0).stream().map(name -> name.trim()).collect(Collectors.toList());
        int outputFactorNumber = nameCoefficients.indexOf(outputFactor.getCategoryIdAndName());
        int inputFactor_1_Number = nameCoefficients.indexOf(inputFactor1.getCategoryIdAndName());
        int inputFactor_2_Number = nameCoefficients.indexOf(inputFactor2.getCategoryIdAndName());

        double r_x1_y1 =
            Double.parseDouble(
                covarianceСoefficients.get(outputFactorNumber).get(inputFactor_1_Number).replace(",", ".")
            );

        double r_x2_y1 =
            Double.parseDouble(
                covarianceСoefficients.get(outputFactorNumber).get(inputFactor_2_Number).replace(",", ".")
            );
        double r_x1_x2 =
            Double.parseDouble(
                covarianceСoefficients.get(inputFactor_1_Number).get(inputFactor_2_Number).replace(",", ".")
            );

        koefficientBeta1 = (r_x1_y1 - r_x2_y1 * r_x1_x2) / (1.0 - r_x1_x2 * r_x1_x2);
        koefficientBeta2 = (r_x2_y1 - r_x1_y1 * r_x1_x2) / (1.0 - r_x1_x2 * r_x1_x2);

        standartDeviationY =
            Double.parseDouble(
                characteristicsSeparatedRawDataTable.get(2).get(outputFactorNumber).replace(",", ".")
            );

        double avarageDeviationY =
            Double.parseDouble(
                characteristicsSeparatedRawDataTable.get(1).get(outputFactorNumber).replace(",", ".")
            );
        double standartDeviationX1 =
            Double.parseDouble(
                characteristicsSeparatedRawDataTable.get(2).get(inputFactor_1_Number).replace(",", ".")
            );

        double avarageDeviationX1 =
            Double.parseDouble(
                characteristicsSeparatedRawDataTable.get(1).get(inputFactor_1_Number).replace(",", ".")
            );
        double standartDeviationX2 =
            Double.parseDouble(
                characteristicsSeparatedRawDataTable.get(2).get(inputFactor_2_Number).replace(",", ".")
            );

        double avarageDeviationX2 =
            Double.parseDouble(
                characteristicsSeparatedRawDataTable.get(1).get(inputFactor_2_Number).replace(",", ".")
            );


        koefficientB1 = koefficientBeta1 * (standartDeviationY / standartDeviationX1);
        koefficientB2 = koefficientBeta2 * (standartDeviationY / standartDeviationX2);
        koefficientA = avarageDeviationY - avarageDeviationX1 * koefficientB1 - avarageDeviationX2 * koefficientB2;
    }

    public double getKoefficientBeta1() {
        return koefficientBeta1;
    }

    public double getKoefficientBeta2() {
        return koefficientBeta2;
    }

    public double getKoefficientA() {
        return koefficientA;
    }

    public double getKoefficientB1() {
        return koefficientB1;
    }

    public double getKoefficientB2() {
        return koefficientB2;
    }

    public double getStandartDeviationY() {
        return standartDeviationY;
    }
}
