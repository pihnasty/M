package neural.network;

import io.serialize.Serializer;
import math.MathP;
import neural.network.layers.HiddenLayer;
import neural.network.layers.InputLayer;
import neural.network.layers.Layer;
import neural.network.layers.OutputLayer;
import neural.network.ws.Ws;
import string.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

import static settings.Settings.Keys.TYPE_LAYER;

public class NeuralManager {

    private NeuralModel neuralModel;
    private static NeuralManager neuralManager = new NeuralManager();

    private List<List<String>> preparedForLearningInputTable = new ArrayList<>();
    private List<List<String>> preparedForLearningOutputTable = new ArrayList<>();
    private List<List<String>> dataTableAfterAnalysisNeuralNet = new ArrayList();
    private List<List<String>> errorsStat = new ArrayList<>();

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

    public void useDeserializeWs(List<Ws> wsS) {
        for(int i=0; i<neuralModel.getLayers().size(); i++) {
            neuralModel.getLayers().get(i).setW(wsS.get(i));
        }
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

    public Double learningNeuralNet() {
        Double [] error2Sum = {0.0};
        Integer countElementInData =  neuralManager.getPreparedForLearningInputTable().size();

        MathP.Counter counter = MathP.getCounter(1);

        neuralManager.getPreparedForLearningInputTable().forEach(
            row -> {
                int count = counter.get();
                if(count>0) {
                    Map<String, Double> rowInputFactor = getRowInputFactor(row);
                    Map<String, Double> rowOutputFactorForLearning = rowOutputFactorForLearning(count);

                    Map<String, Double> outputFactorsRowCalculate = neuralModel.forwardPropagation(rowInputFactor);
                    Map<String, Double> errorOutputFactorsRow = neuralModel.forwardPropagationErrors( rowOutputFactorForLearning,  outputFactorsRowCalculate);
                    neuralModel.backPropagation(errorOutputFactorsRow);
                    Double error2 = 0.0;
                    for (double value : errorOutputFactorsRow.values()) {
                        error2 += value*value;
                    }
                    error2Sum[0] += error2 ;
                }
            }
        );
        Double MSE = error2Sum[0]/countElementInData;     //Math.sqrt(error2Sum[0]/countElementInData);
        return MSE;
    }

    private Map<String, Double> rowOutputFactorForLearning(int count) {
        Map<String, Double> rowOutputFactorForLearning = new HashMap<>();
        List<String> row = neuralManager.getPreparedForLearningOutputTable().get(count);
        List<String> headerRow = neuralManager.getPreparedForLearningOutputTable().get(0);
        for(int i=0; i<row.size(); i++) {
            String key = headerRow.get(i).trim();
            Double doubleValue = Double.parseDouble(row.get(i));
            rowOutputFactorForLearning.put(key, doubleValue);
        }
        return rowOutputFactorForLearning;
    }

    private Map<String, Double> getRowInputFactor(List<String> row) {
        Map<String, Double> rowInputFactor = new HashMap<>();
        for (int i=0; i<row.size(); i++) {
            rowInputFactor.put(
                neuralManager.getPreparedForLearningInputTable().get(0).get(i).trim()
                ,Double.parseDouble(row.get(i))
            );
        }
        return rowInputFactor;
    }

    public void predictionNeuralNet() {
        MathP.Counter counter = MathP.getCounter(1);

        dataTableAfterAnalysisNeuralNet = new ArrayList<>();
        int sizeLayers = neuralModel.getLayers().size();

        List<String> headerOriginal = new ArrayList<>();
        List<String> headerPrediction = new ArrayList<>();

        neuralModel.getLayers().get(sizeLayers - 1).getNodes().forEach(node -> {
                String headerValue = node.getFactorName().trim();
                headerOriginal.add(headerValue);
                headerPrediction.add(headerValue + "A");
            }
        );

        dataTableAfterAnalysisNeuralNet.add(headerPrediction);
        neuralManager.getPreparedForLearningInputTable().forEach(
            row -> {
                int count = counter.get();
                if(count>0) {

                    Map<String, Double> rowInputFactor = getRowInputFactor(row);
                    Map<String, Double> outputFactorsRowCalculate = neuralModel.forwardPropagation(rowInputFactor);

                    List<String> rowString = new ArrayList<>();
                    for(int i=0; i<headerOriginal.size(); i++) {
                        rowString.add(StringUtil.getDoubleFormatValue(outputFactorsRowCalculate.get(headerOriginal.get(i)),headerPrediction.get(i)));
                    }
                    dataTableAfterAnalysisNeuralNet.add(rowString);
                }
            }
        );
    }

    public void serializeNeuralNet(List<Ws> wsS, String path, String fileName) {
        Serializer.serialize(wsS, path, fileName);
    }

    public List<Ws> deserializeNeuralNet(String path, String fileName) {
        return (List<Ws>) Serializer.deserialize(path, fileName);
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

    public List<List<String>> getDataTableAfterAnalysisNeuralNet() {
        return dataTableAfterAnalysisNeuralNet;
    }

    public void setDataTableAfterAnalysisNeuralNet(List<List<String>> dataTableAfterAnalysisNeuralNet) {
        this.dataTableAfterAnalysisNeuralNet = dataTableAfterAnalysisNeuralNet;
    }

    public List<List<String>> getErrorsStat() {
        return errorsStat;
    }

    public void setErrorsStat(List<List<String>> errorsStat) {
        this.errorsStat = errorsStat;
    }
}








