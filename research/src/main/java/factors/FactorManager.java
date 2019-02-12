package factors;


import common.ProjectConstant;
import math.MathP;
import settings.ProviderSettings;
import settings.Settings;
import string.StringUtil;

import java.util.*;

import java.util.stream.Collectors;

import static settings.ProviderSettings.getProjectSettingsMapValue;


public class FactorManager {

    private Map<String,Map<String,String>> mapFactorNames ;

    private List<List<String>> separatedRawDataTable;

    private Factor numberFactor;

    private List<List<String>> separatedRawDataTableWithOutHeader;
    private List<List<String>> normalizedSeparatedRawDataTable;
    private List<String> header;
    private Map<String,Factor> factors;
    private List<List<String>> covarianceСoefficients;
    private List<List<String>> significanceOfFactors;


    private List<List<String>> characteristicsSeparatedRawDataTable;
    private List<List<String>> characteristicsDimensionlessSeparatedRawDataTable;



    public FactorManager(List<List<String>> separatedRawDataTable) {
        this.separatedRawDataTable = separatedRawDataTable;
        initialFactorNames();
        initialNumberFactor();
        initialFactors();
        formedNormalazeSeparatedRawDataTable();
        formedCovarianceСoefficients();


        formedChacteristicsSeparatedRawDataTable();
        formedChacteristicsDimensionlessSeparatedRawDataTable();
    }


    private void initialFactorNames() {
        String splitor = ".".equals(getProjectSettingsMapValue(Settings.Keys.DEFAULT_SPLIT_FOR_CATEGORY_AND_NAME))
            ? "\\." : getProjectSettingsMapValue(Settings.Keys.DEFAULT_SPLIT_FOR_CATEGORY_AND_NAME);

        List<String> fullFactorNames = separatedRawDataTable.get(0);
        mapFactorNames = fullFactorNames.stream().collect(
            Collectors.toMap(
                String::trim
                , fullFactorName -> {
                    String[] idCategorySplitName = fullFactorName.split(splitor);
                    String idCategory = idCategorySplitName[0].trim();
                    String name = idCategorySplitName[1].trim();

                    Map<String,String> mapCategoryAndName = new HashMap<>();
                    mapCategoryAndName.put(ProjectConstant.Factors.ID,idCategory);
                    mapCategoryAndName.put(ProjectConstant.Factors.NAME,name);
                    return mapCategoryAndName;
                }
            ));
    }


    private void initialNumberFactor() {

        String fullNumberFactorName = ProviderSettings.getProjectSettingsMapValue(Settings.Keys.NAME_NUMBER_FACTOR);

        if (mapFactorNames.containsKey(fullNumberFactorName)) {
            numberFactor = new Factor(fullNumberFactorName, mapFactorNames.get(fullNumberFactorName));

            separatedRawDataTableWithOutHeader = new ArrayList<>(separatedRawDataTable);
            header = separatedRawDataTableWithOutHeader.remove(0).stream().map(String::trim).collect(Collectors.toList());
            int number = header.indexOf(fullNumberFactorName);


            MathP.Counter counter = MathP.getCounter(1, 1);
            Map<String, String> values = new HashMap<>();
            separatedRawDataTableWithOutHeader.forEach(
                row ->
                    values.put(row.get(number),  counter.get().toString())
            );
            numberFactor.setValues(values);
        } else {
            throw new NullPointerException();
        }
    }


    private void initialFactors() {
        factors = new HashMap<>();
        header.stream()
            .filter(categoryIdAndName -> !categoryIdAndName.equalsIgnoreCase(numberFactor.getCategoryIdAndName()))
            .forEach(
                categoryIdAndName -> {
                    Factor factor = new Factor(categoryIdAndName, mapFactorNames.get(categoryIdAndName));
                    factors.put(categoryIdAndName, factor);
                    int countNumberFactor = header.indexOf(numberFactor.getCategoryIdAndName());
                    int countFactor = header.indexOf(categoryIdAndName);

                    Map<String, String> values = new HashMap<>();
                    separatedRawDataTableWithOutHeader.forEach(row -> values.put(row.get(countNumberFactor)
                        , row.get(countFactor)));
                    factor.setValues(values);
                    factor.setDimensionlessValue();
                }
            );
    }


    private void formedNormalazeSeparatedRawDataTable() {
        normalizedSeparatedRawDataTable = new ArrayList<>();
        List<String> formatedHeader = header.stream().map(this::getStringFormatValue).collect(Collectors.toList());
        normalizedSeparatedRawDataTable.add(formatedHeader);
        numberFactor.getValues().entrySet().stream().sorted(
            (entryLeft, enrryRight) -> {
                double entryLeftValue = Double.parseDouble(entryLeft.getValue());
                double entryRightValue = Double.parseDouble(enrryRight.getValue());
                return (int) (entryLeftValue - entryRightValue);
            }
        ).forEach(
            entry -> {
                List<String> row = new ArrayList<>();
                header.forEach(categoryIdAndName -> {
                        if (numberFactor.getCategoryIdAndName().equals(categoryIdAndName)) {
                            String key = entry.getKey();
                            row.add( getStringFormatValue(key ));
                        } else {
                            try {
                                Double value = factors.get(categoryIdAndName).getDimensionlessValue().get(entry.getKey());

                                row.add(Objects.isNull(value)
                                    ? getStringFormatValue("-", categoryIdAndName)
                                    : getDoubleFormatValue(value,categoryIdAndName)
                                );
                            } catch (Exception e) {
                                    throw e;
                            }

                        }
                    }
                );
                normalizedSeparatedRawDataTable.add(row);
            }
        );
    }

    private void formedChacteristicsSeparatedRawDataTable() {
        characteristicsSeparatedRawDataTable = new ArrayList<>();
        List<String> formatedHeader = header.stream().map(this::getStringFormatValue).collect(Collectors.toList());
        characteristicsSeparatedRawDataTable.add(formatedHeader);

        List<String> averageValueRow = new ArrayList<>();
        header.forEach(
            categoryIdAndName-> {
                if (categoryIdAndName.equals(numberFactor.getCategoryIdAndName())) {
                    averageValueRow.add(getStringFormatValue (ProjectConstant.Factors.AVERAGE,categoryIdAndName) );
                } else {
                    averageValueRow.add(getDoubleFormatValue(factors.get(categoryIdAndName).getAverageValue(), categoryIdAndName));
                }
            }
        );
        characteristicsSeparatedRawDataTable.add(averageValueRow);

        List<String> standardDeviationRow = new ArrayList<>();
        header.forEach(
            categoryIdAndName-> {
                if (categoryIdAndName.equals(numberFactor.getCategoryIdAndName())) {
                    standardDeviationRow.add(getStringFormatValue (ProjectConstant.Factors.STANDARDDEVIATION,categoryIdAndName) );
                } else {
                    standardDeviationRow.add(getDoubleFormatValue(factors.get(categoryIdAndName).getStandardDeviation(), categoryIdAndName));
                }
            }
        );
        characteristicsSeparatedRawDataTable.add(standardDeviationRow);

        List<String> maxDeviationRow = new ArrayList<>();
        header.forEach(
            categoryIdAndName-> {
                if (categoryIdAndName.equals(numberFactor.getCategoryIdAndName())) {
                    maxDeviationRow.add(getStringFormatValue (ProjectConstant.Factors.MAX,categoryIdAndName) );
                } else {
                    maxDeviationRow.add(getDoubleFormatValue(factors.get(categoryIdAndName).getMaxValue(), categoryIdAndName));
                }
            }
        );
        characteristicsSeparatedRawDataTable.add(maxDeviationRow);

        List<String> minDeviationRow = new ArrayList<>();
        header.forEach(
            categoryIdAndName-> {
                if (categoryIdAndName.equals(numberFactor.getCategoryIdAndName())) {
                    minDeviationRow.add(getStringFormatValue (ProjectConstant.Factors.MIN,categoryIdAndName) );
                } else {
                    minDeviationRow.add(getDoubleFormatValue(factors.get(categoryIdAndName).getMinValue(), categoryIdAndName));
                }
            }
        );
        characteristicsSeparatedRawDataTable.add(minDeviationRow);
    }

    private void formedChacteristicsDimensionlessSeparatedRawDataTable() {
        characteristicsDimensionlessSeparatedRawDataTable = new ArrayList<>();
        List<String> formatedHeader = header.stream().map(this::getStringFormatValue).collect(Collectors.toList());
        characteristicsDimensionlessSeparatedRawDataTable.add(formatedHeader);

        List<String> averageValueRow = new ArrayList<>();
        header.forEach(
            categoryIdAndName-> {
                if (categoryIdAndName.equals(numberFactor.getCategoryIdAndName())) {
                    averageValueRow.add(getStringFormatValue (ProjectConstant.Factors.AVERAGE,categoryIdAndName) );
                } else {
                    averageValueRow.add(getDoubleFormatValue(factors.get(categoryIdAndName).getDimensionlessAverageValue(), categoryIdAndName));
                }
            }
        );
        characteristicsDimensionlessSeparatedRawDataTable.add(averageValueRow);

        List<String> standardDeviationRow = new ArrayList<>();
        header.forEach(
            categoryIdAndName-> {
                if (categoryIdAndName.equals(numberFactor.getCategoryIdAndName())) {
                    standardDeviationRow.add(getStringFormatValue (ProjectConstant.Factors.STANDARDDEVIATION,categoryIdAndName) );
                } else {
                    standardDeviationRow.add(getDoubleFormatValue(factors.get(categoryIdAndName).getDimensionlessStandardDeviation(), categoryIdAndName));
                }
            }
        );
        characteristicsDimensionlessSeparatedRawDataTable.add(standardDeviationRow);

        List<String> maxDeviationRow = new ArrayList<>();
        header.forEach(
            categoryIdAndName-> {
                if (categoryIdAndName.equals(numberFactor.getCategoryIdAndName())) {
                    maxDeviationRow.add(getStringFormatValue (ProjectConstant.Factors.MAX,categoryIdAndName) );
                } else {
                    maxDeviationRow.add(getDoubleFormatValue(factors.get(categoryIdAndName).getDimensionlessMaxValue(), categoryIdAndName));
                }
            }
        );
        characteristicsDimensionlessSeparatedRawDataTable.add(maxDeviationRow);

        List<String> minDeviationRow = new ArrayList<>();
        header.forEach(
            categoryIdAndName-> {
                if (categoryIdAndName.equals(numberFactor.getCategoryIdAndName())) {
                    minDeviationRow.add(getStringFormatValue (ProjectConstant.Factors.MIN,categoryIdAndName) );
                } else {
                    minDeviationRow.add(getDoubleFormatValue(factors.get(categoryIdAndName).getDimensionlessMinValue(), categoryIdAndName));
                }
            }
        );
        characteristicsDimensionlessSeparatedRawDataTable.add(minDeviationRow);
    }

    public List<List<String>> getNormalazeSeparatedRawDataTable() {
        return normalizedSeparatedRawDataTable;
    }

    public void formedCovarianceСoefficients() {

        int firstColumnLength = header.stream().max(Comparator.comparing(String::length)).get().length();

        List<String> formatedHeader = header.stream().map(this::getStringFormatValue
        ).collect(Collectors.toList());

        formatedHeader.set(0, String.format(" " + "%" + (formatedHeader.get(0).length() < firstColumnLength
            ? firstColumnLength : formatedHeader.get(0).length()) + "s ", formatedHeader.get(0)));
        covarianceСoefficients = new ArrayList<>();
        covarianceСoefficients.add(formatedHeader);

        List<String> headerWithOutNumberFactor = new ArrayList<>(header);
        headerWithOutNumberFactor.remove(numberFactor.getCategoryIdAndName());

        significanceOfFactors = new ArrayList<>();

        headerWithOutNumberFactor.forEach(
            verticalFactorCategoryIdAndName -> {
                List<String> row = new ArrayList<>();

                Factor verticalFactor = factors.get(verticalFactorCategoryIdAndName);

                headerWithOutNumberFactor.forEach(
                    horizontalFactorCategoryIdAndName -> {
                        if (row.isEmpty()) {
                            row.add(getStringFormatValue(verticalFactorCategoryIdAndName, firstColumnLength));
                        }
                        Factor horizontalFactor = factors.get(horizontalFactorCategoryIdAndName);

                        Double[] covarianceСoefficientSumma = {0.0};
                        int numberElements = 1;
                        try {
                            Map<String, Double> horizontalDimensionlessValues = horizontalFactor.getDimensionlessValue();
                            Map<String, Double> verticalDimensionlessValues = verticalFactor.getDimensionlessValue();
                            List<String> keys = new ArrayList<>(verticalDimensionlessValues.keySet());


                            MathP.Counter counter = MathP.getCounter(1, 1);
                            keys.forEach(
                                key -> {
                                    Double horizontalValue = horizontalDimensionlessValues.get(key);
                                    Double verticalValue = verticalDimensionlessValues.get(key);
                                    if (Objects.nonNull(horizontalValue) && Objects.nonNull(verticalValue)) {
                                        covarianceСoefficientSumma[0] += horizontalValue * verticalValue;
                                        counter.get();

                                    }
                                }
                            );
                            numberElements = counter.get()-1;
                            numberElements = numberElements==0?1:numberElements;
                        } catch (Exception e) {
                            throw e;
                        }

                        row.add(getDoubleFormatValue(covarianceСoefficientSumma[0] / numberElements , horizontalFactorCategoryIdAndName));


                        formedSignificanceOfFactors (covarianceСoefficientSumma[0] / numberElements
                            , verticalFactorCategoryIdAndName
                            ,  horizontalFactorCategoryIdAndName, firstColumnLength);
                    }
                );
                covarianceСoefficients.add(row);
            }
        );
    }

    public List<List<String>> getCovarianceCoefficients() {
        return covarianceСoefficients;
    }


    private String getStringFormatValue(String value) {
        return StringUtil.getStringFormat(value
            ,Integer.parseInt(ProviderSettings.getProjectSettingsMapValue(Settings.Keys.LENGTH_CELL))
        );
    }

    private String getStringFormatValue(String value, int length) {
        return StringUtil.getStringFormat(value,length);
    }

    private String getDoubleFormatValue (Double value, String headerValue) {
        return StringUtil.getDoubleFormatValue(value, headerValue
            , ProviderSettings.getProjectSettingsMapValue(Settings.Keys.LENGTH_CELL));
    }


    private String getStringFormatValue (String value, String headerValue) {
        int lengthCell = Integer.parseInt(ProviderSettings.getProjectSettingsMapValue(Settings.Keys.LENGTH_CELL));
        int lengthHeaderValue = headerValue.length();
        return  String.format(" " + "%"
            + (lengthHeaderValue<lengthCell?lengthCell:lengthHeaderValue)
            + "s ", value);
    }

    private String getDoubleFormatValue (Double value, int lengthHeaderValue) {
        int lengthCell = Integer.parseInt(ProviderSettings.getProjectSettingsMapValue(Settings.Keys.LENGTH_CELL));

        return  String.format(" " + "%"
            + (lengthHeaderValue<lengthCell?lengthCell:lengthHeaderValue)
            + ".2f ", value);
    }


    public List<List<String>> getCharacteristicsSeparatedRawDataTable() {
        return characteristicsSeparatedRawDataTable;
    }

    public List<List<String>> getCharacteristicsDimensionlessSeparatedRawDataTable() {
        return characteristicsDimensionlessSeparatedRawDataTable;
    }

    private void formedSignificanceOfFactors (Double covarianceСoefficient, String verticalFactorCategoryIdAndName, String horizontalFactorCategoryIdAndName, int length) {
        if(!verticalFactorCategoryIdAndName.equals(horizontalFactorCategoryIdAndName)) {
            List<String> rowSignificanceOfFactors = new ArrayList<>();
            rowSignificanceOfFactors.add(getDoubleFormatValue(covarianceСoefficient, length));
            rowSignificanceOfFactors.add(getStringFormatValue(verticalFactorCategoryIdAndName, length));
            rowSignificanceOfFactors.add(getStringFormatValue(horizontalFactorCategoryIdAndName, length));
            significanceOfFactors.add(rowSignificanceOfFactors);
        }
    }

    public List<List<String>> getSignificanceOfFactors() {
        List<List<String>> list;
      try {
          list =
              significanceOfFactors.stream().sorted(
                  (row1, row2) -> {
                      Double doubleRow1 = Double.parseDouble(row1.get(0).replace(",", "."));
                      Double doubleRow2 = Double.parseDouble(row2.get(0).replace(",", "."));
                    return   (doubleRow1 > doubleRow2) ?  1 : -1;
                  }
              ).collect(Collectors.toList());
      }catch (IllegalArgumentException e) {
          throw e;
      }

        return list;
    }


    public Map<String, Factor> getFactors() {
        return factors;
    }
}
