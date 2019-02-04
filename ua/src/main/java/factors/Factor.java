package factors;

import com.sun.javafx.collections.MappingChange;
import main.ProjectConstant;
import settings.ProviderSettings;

import java.util.*;

public class Factor {
    private String categoryIdAndName;
    private String name;
    private String categoryId;
    private String category;

    private double averageValue;
    private double maxValue;
    private double minValue;
    private double standardDeviation;
    private Map<String,String> values;

    private double dimensionlessAverageValue;
    private double dimensionlessMaxValue;
    private double dimensionlessMinValue;
    private double dimensionlessStandardDeviation;
    private Map<String,Double> dimensionlessValue;

    private int numberRows;

    public Factor(String name) {
        this.name = name;
    }

    public Factor(String categoryIdAndName, Map<String,String> mapNameAdnCategore) {
        this.categoryIdAndName=categoryIdAndName;
        this.name = mapNameAdnCategore.get(ProjectConstant.Factors.NAME);
        this.categoryId=mapNameAdnCategore.get(ProjectConstant.Factors.ID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(double averageValue) {
        this.averageValue = averageValue;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public Map<String, Double> getDimensionlessValue() {
        return dimensionlessValue;
    }

    public void setDimensionlessValue() {

        setCharacteristicsFactor();

        dimensionlessValue = new HashMap<>();

        values.keySet().forEach(key -> {
                if (values.get(key).trim().equals("-")) {
                    dimensionlessValue.put(key, null);
                } else {
                    dimensionlessValue.put(key, (Double.parseDouble(values.get(key)) - averageValue) / standardDeviation);
                }
            }
        );

        setDimensionlessCharacteristicsFactor();
    }

    private void setCharacteristicsFactor() {
        numberRows = getNumberRows();

        try {
            averageValue = values.values().stream().filter(value -> !value.trim().equals("-"))
                .map(Double::parseDouble).reduce(0.0, (acc, element) -> acc + element) / numberRows;


            standardDeviation = Math.sqrt(
                values.values().stream().filter(value -> !value.trim().equals("-"))
                    .map(Double::parseDouble).reduce(0.0, (
                    acc, element) -> acc + (element - averageValue) * (element - averageValue)
                ) / numberRows
            );

            maxValue =
                Double.parseDouble(
                values.values().stream().filter(value -> !value.trim().equals("-"))
                .max(Comparator.comparing(Double::parseDouble) ).get()
                );

            minValue =
                Double.parseDouble(
                    values.values().stream().filter(value -> !value.trim().equals("-"))
                        .min(Comparator.comparing(Double::parseDouble) ).get()
                );


            System.out.println("хххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххххх");


        } catch (NumberFormatException e) {
            System.out.println("12345678");
        }
    }


    private void setDimensionlessCharacteristicsFactor() {
        numberRows = getNumberRows();

        try {
            dimensionlessAverageValue = dimensionlessValue.values().stream().filter(Objects::nonNull)
                .reduce(0.0, (acc, element) -> acc + element) / numberRows;

            dimensionlessStandardDeviation = Math.sqrt(
                dimensionlessValue.values().stream().filter(Objects::nonNull)
                    .reduce(0.0, (
                    acc, element) -> acc + (element - dimensionlessAverageValue) * (element - dimensionlessAverageValue)
                ) / numberRows
            );

            dimensionlessMaxValue =
                    dimensionlessValue.values().stream().filter(Objects::nonNull).max(Comparator.comparing(e->e)).get();

            dimensionlessMinValue =
                dimensionlessValue.values().stream().filter(Objects::nonNull).min(Comparator.comparing(e->e)).get();


        } catch (NumberFormatException e) {
            System.out.println("12345678");
        }
    }


    private int getNumberRows() {
        return (int) values.entrySet().stream().filter(entry->!entry.getValue().trim().equals("-")).count();
    }

    public String getCategoryIdAndName() {
        return categoryIdAndName;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getDimensionlessAverageValue() {
        return dimensionlessAverageValue;
    }

    public double getDimensionlessMaxValue() {
        return dimensionlessMaxValue;
    }

    public double getDimensionlessMinValue() {
        return dimensionlessMinValue;
    }

    public double getDimensionlessStandardDeviation() {
        return dimensionlessStandardDeviation;
    }
}
