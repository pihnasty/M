package models;

import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OneParameterModel {
    private List<List<String>> koefficientA;
    private List<List<String>> koefficientB;


    public void calculateKoefficientB(List<List<String>> covarianceСoefficients
        , List<List<String>> characteristicsSeparatedRawDataTable) {

        koefficientA = new ArrayList<>();
        koefficientB = new ArrayList<>();

        covarianceСoefficients.forEach(
            row-> {
                List<String> rowA = new ArrayList<>();
                row.forEach(                    cell-> rowA.add(cell)     );
                koefficientA.add(rowA);
            }
        );

        covarianceСoefficients.forEach(
            row-> {
                List<String> rowB = new ArrayList<>();
                row.forEach(                    cell-> rowB.add(cell)     );
                koefficientB.add(rowB);
            }
        );





        for (int i1 = 1; i1 < koefficientA.size(); i1++) {

            for (int i2 =1 ; i2 < koefficientA.get(0).size(); i2++) {
                String stringValue = koefficientA.get(i1).get(i2).replace(",",".");

                double covarianceСoefficient = Double.parseDouble(stringValue);

                String stringStandartDeviationY = characteristicsSeparatedRawDataTable.get(2).get(i1).replace(",",".");
                String stringStandartDeviationX = characteristicsSeparatedRawDataTable.get(2).get(i2).replace(",",".");

                double standartDeviationY = Double.parseDouble(stringStandartDeviationY);
                double standartDeviationX = Double.parseDouble(stringStandartDeviationX);

                double b = covarianceСoefficient*standartDeviationY/standartDeviationX;
                koefficientB.get(i1).set(i2,getDoubleFormatValue(b,stringValue));


                String stringAvarageDeviationY = characteristicsSeparatedRawDataTable.get(1).get(i1).replace(",",".");
                String stringAvarageDeviationX = characteristicsSeparatedRawDataTable.get(1).get(i2).replace(",",".");

                double avarageDeviationY = Double.parseDouble(stringAvarageDeviationY);
                double avarageDeviationX = Double.parseDouble(stringAvarageDeviationX);

                double a = avarageDeviationY - b*avarageDeviationX;
                koefficientA.get(i1).set(i2,getDoubleFormatValue(a,stringValue));

            }
        }
    }

    private String getDoubleFormatValue (Double value, String headerValue) {
        return StringUtil.getDoubleFormatValue(value, headerValue.substring(1,headerValue.length()-1)
            , ProviderSettings.getProjectSettingsMapValue(Settings.Keys.LENGTH_CELL));
    }

    public List<List<String>> getKoefficientA() {
        return koefficientA;
    }

    public List<List<String>> getKoefficientB() {
        return koefficientB;
    }
}
