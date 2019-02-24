package models;

import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.util.ArrayList;
import java.util.List;

import static collection.ListP.deepCloneArrayList;

public class TwoParameterModel {
    private List<List<String>> koefficientBeta1;
    private List<List<String>> koefficientBeta2;

    private List<List<String>> koefficientA;
    private List<List<String>> koefficientB1;
    private List<List<String>> koefficientB2;


    public void calculateKoefficientsBetta(List<List<String>> covarianceСoefficients ) {

        koefficientBeta1 = deepCloneArrayList(covarianceСoefficients);
        koefficientBeta2 = deepCloneArrayList(covarianceСoefficients);

//        for (int i1 = 1; i1 < covarianceСoefficients.size(); i1++) {
//
//            for (int i2 =1 ; i2 < covarianceСoefficients.get(0).size(); i2++) {
//
//                String stringValue = koefficientA.get(i1).get(i2).replace(",",".");
//
//                double covarianceСoefficient = Double.parseDouble(stringValue);
//
//                String stringStandartDeviationY = characteristicsSeparatedRawDataTable.get(2).get(i1).replace(",",".");
//                String stringStandartDeviationX = characteristicsSeparatedRawDataTable.get(2).get(i2).replace(",",".");
//
//                double standartDeviationY = Double.parseDouble(stringStandartDeviationY);
//                double standartDeviationX = Double.parseDouble(stringStandartDeviationX);
//
//                double b = covarianceСoefficient*standartDeviationY/standartDeviationX;
//                koefficientB1.get(i1).set(i2,getDoubleFormatValue(b,stringValue));
//
//
//                String stringAvarageDeviationY = characteristicsSeparatedRawDataTable.get(1).get(i1).replace(",",".");
//                String stringAvarageDeviationX = characteristicsSeparatedRawDataTable.get(1).get(i2).replace(",",".");
//
//                double avarageDeviationY = Double.parseDouble(stringAvarageDeviationY);
//                double avarageDeviationX = Double.parseDouble(stringAvarageDeviationX);
//
//                double a = avarageDeviationY - b*avarageDeviationX;
//                koefficientA.get(i1).set(i2,getDoubleFormatValue(a,stringValue));
//
//            }
//        }




    }



    private String getDoubleFormatValue (Double value, String headerValue) {
        return StringUtil.getDoubleFormatValue(value, headerValue.substring(1,headerValue.length()-1)
            , ProviderSettings.getProjectSettingsMapValue(Settings.Keys.LENGTH_CELL));
    }


}
