package models;

import settings.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Criterion {
    private Map<String,Double> map = new HashMap<>();
    private List<List<String>> separatedRawDataTable;
    private List<String> inputFactors;
    public Criterion (List<List<String>> separatedRawDataTable, List<String> inputFactors) {
        this.separatedRawDataTable=separatedRawDataTable;
        this.inputFactors=inputFactors;
        calculate();
    }

    public void calculate() {

        map.put(Settings.Values.NUMBER_OBSERVATIONS,numberObservations());
        map.put(Settings.Values.NUMBER_CONSTRAINTS,numberConstraints());
    }

    private double numberObservations() {
        return separatedRawDataTable.size();
    }

    private double numberConstraints() {
        return inputFactors.size()+1;
    }

    public double get(String key) {
        return map.get(key);
    }


    // Settings.Values.NUMBER_CONSTRAINTS,

}
