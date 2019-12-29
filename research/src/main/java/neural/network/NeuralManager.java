package neural.network;

import math.MathP;
import neural.network.layers.HiddenLayer;
import neural.network.layers.InputLayer;
import neural.network.layers.Layer;
import neural.network.layers.OutputLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static settings.Settings.Keys.TYPE_LAYER;

public class NeuralManager {

    private NeuralModel neuralModel;
    private static NeuralManager neuralManager = new NeuralManager();

    private List<List<String>> preparedForLearningInputTable = new ArrayList<>();
    private List<List<String>> preparedForLearningOutputTable = new ArrayList<>();

    public static NeuralManager getManager() {
        return neuralManager;
    }

    private NeuralManager() {
    }


    public void buildArchitecture(List<String> inputFactorNames, Map<String, Map<String, String>> hiddenLevelNamesWithExtParameters, List<String> outputFactorNames) {

        List<Layer> layers = new ArrayList<>();
        layers.add(new InputLayer(inputFactorNames));

        List<Integer> intValueKeys = hiddenLevelNamesWithExtParameters.keySet().stream().map(Integer::parseInt).sorted().collect(Collectors.toList());

        int numberHeadLayer = getNumberHeadLayer(hiddenLevelNamesWithExtParameters, intValueKeys);
        int numberTailLayer = getNumberTailLayer(hiddenLevelNamesWithExtParameters, intValueKeys);

        Integer idBetweenLayers = intValueKeys.get(0);
        for (Integer id = numberHeadLayer; id < numberTailLayer; id++) {
            if (intValueKeys.contains(id)) {
                idBetweenLayers = id;
        }
            layers.add(
                new HiddenLayer(id
                    , hiddenLevelNamesWithExtParameters.get(idBetweenLayers.toString())
                    , layers.get(layers.size()-1)
                )
            );
        }

        idBetweenLayers = numberTailLayer;
        layers.add(new OutputLayer(numberTailLayer
            , hiddenLevelNamesWithExtParameters.get(idBetweenLayers.toString())
            ,outputFactorNames, layers.get(layers.size()-1)));
        neuralModel.setLayers(layers);
    }

    public void randomInitWs() {
        neuralModel.getLayers().forEach(Layer::initRandomW);
    }


    private int getNumberHeadLayer(Map<String, Map<String, String>> hiddenLevelNamesWithExtParameters, List<Integer> intValueKeys) {
        int numberEndLayer;
        Optional<Integer> result = intValueKeys.stream().filter(valueKey-> "head".equalsIgnoreCase(hiddenLevelNamesWithExtParameters.get(valueKey.toString()).get(TYPE_LAYER))).findFirst();
        numberEndLayer = result.isPresent() ? result.get() : intValueKeys.get(intValueKeys.size()-1);
        return numberEndLayer;
    }

    private int getNumberTailLayer(Map<String, Map<String, String>> hiddenLevelNamesWithExtParameters, List<Integer> intValueKeys) {
        int numberEndLayer;
        Optional<Integer> result = intValueKeys.stream().filter(valueKey-> "tail".equalsIgnoreCase(hiddenLevelNamesWithExtParameters.get(valueKey.toString()).get(TYPE_LAYER))).findFirst();
        numberEndLayer = result.isPresent() ? result.get() : intValueKeys.get(intValueKeys.size()-1);
        return numberEndLayer;
    }

    public void setNeuralModel(NeuralModel neuralModel) {
        this.neuralModel = neuralModel;
    }

    public void prepareForLearningTable(List<String> inputFactors, List<String> outputFactors, List<List<String>> separatedRawDataTable) {

        List<Integer> inputFactorNumbers = new ArrayList<>();
        List<Integer> outputFactorNumbers = new ArrayList<>();


        for(int i=0; i<separatedRawDataTable.get(0).size();i++) {
            for(String inputFactor: inputFactors) {
                if(inputFactor.trim().equals(separatedRawDataTable.get(0).get(i).trim())) {
                    inputFactorNumbers.add(i);
                    continue;
                }
            }
            for(String factor: outputFactors) {
                if (factor.trim().equals(separatedRawDataTable.get(0).get(i).trim())) {
                    outputFactorNumbers.add(i);
                    continue;
                }
            }
        }


        preparedForLearningInputTable = separatedRawDataTable.stream().map(
            row -> {
                List<String> prepareRow = new ArrayList<>();
                inputFactorNumbers.forEach(i->prepareRow.add(row.get(i)));
                return prepareRow;
            }
        ).collect(Collectors.toList());

        preparedForLearningOutputTable = separatedRawDataTable.stream().map(
            row -> {
                List<String> prepareRow = new ArrayList<>();
                outputFactorNumbers.forEach(i->prepareRow.add(row.get(i)));
                return prepareRow;
            }
        ).collect(Collectors.toList());
    }

    public void learningNeuralNet() {
        MathP.Counter counter = MathP.getCounter(1);
        neuralManager.getPreparedForLearningInputTable().forEach(
            row -> {
                if(counter.get()>0) {
                    List<Double> rowOutPutFactor = new ArrayList<>();
                    List<Double> rowInputFactor = new ArrayList<>();
                    row.forEach(stringValue -> {
                        Double doubleValue = Double.parseDouble(stringValue);
                        rowInputFactor.add(doubleValue);
                    });
                    neuralModel.forwardPropagation(rowInputFactor, rowOutPutFactor);
                }
            }
        );
    }


    public List<List<String>> getPreparedForLearningInputTable() {
        return preparedForLearningInputTable;
    }

    public void setPreparedForLearningInputTable(List<List<String>> preparedForLearningInputTable) {
        this.preparedForLearningInputTable = preparedForLearningInputTable;
    }

    public List<List<String>> getPreparedForLearningOutputTable() {
        return preparedForLearningOutputTable;
    }

    public void setPreparedForLearningOutputTable(List<List<String>> preparedForLearningOutputTable) {
        this.preparedForLearningOutputTable = preparedForLearningOutputTable;
    }

    public NeuralModel getNeuralModel() {
        return neuralModel;
    }
}








