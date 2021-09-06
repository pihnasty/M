package neural.network;

import io.serialize.Serializer;
import math.MathP;
import neural.network.exceptions.NeuralNetElementCloneNotSupportedException;
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

    public static Boolean[] loggerFlag = new Boolean[1];
    private NeuralModel neuralModel;
    private static NeuralManager neuralManager = new NeuralManager();

    private List<List<String>> preparedForLearningInputTable = new ArrayList<>();
    private List<List<String>> preparedForLearningOutputTable = new ArrayList<>();
    private List<List<String>> dataTableAfterAnalysisNeuralNet = new ArrayList();
    private List<List<String>> errorsStat = new ArrayList<>();
    private int batchSize;

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

        Integer countElementInData =  neuralManager.getPreparedForLearningInputTable().size();
        Double [] error2Sum = {0.0};
//        if(batchSize==1) {
//            learningNeuralNetSequentialMode(error2Sum);
//        } else {
//            learningNeuralNetBatchMode(error2Sum);
//        }
        learningNeuralNetBatchMode(error2Sum);
        Double MSE = error2Sum[0]/countElementInData;     //Math.sqrt(error2Sum[0]/countElementInData);
        return MSE;
    }

    private void learningNeuralNetSequentialMode(Double[] error2Sum) {
        MathP.Counter counter = MathP.getCounter(1);
        neuralManager.getPreparedForLearningInputTable().forEach(
            row -> {
                int count = counter.get();
                if(count>0) {
                    loggerFlagSetByCount(count);

                    //region    errorOutputFactorsRow  e[j] = d[j] - y[j]
                    Map<String, Double> rowInputFactor = getRowInputFactor(row);
                    // d[j]
                    Map<String, Double> rowOutputFactorForLearning = rowOutputFactorForLearning(count);
                    // y[j]
                    Map<String, Double> outputFactorsRowCalculate = neuralModel.forwardPropagation(rowInputFactor);
                    // e[j] = d[j] - y[j]
                    Map<String, Double> errorOutputFactorsRow = neuralModel.outputLayerNeuronErrors(rowOutputFactorForLearning, outputFactorsRowCalculate);
                    //endregion

                    NeuralModelService.backPropagationSequentialMode(errorOutputFactorsRow, neuralModel.getLayers());
                    Double error2 = 0.0;
                    if(count/1*1==count) {
                    //    System.out.println("count=" + count + " " + neuralModel.getLayers().get(2).getW().getListWs().get(0));
                    }
                    for (double value : errorOutputFactorsRow.values()) {
                        error2 += value*value;
                    }
                    error2Sum[0] += error2 ;
                }
            }
        );
    }

    private void loggerFlagSetByCount(int count) {
        if (count == 1000) {
            loggerFlag[0] = true;
        } else {
            loggerFlag[0] = false;
        }
    }

    private void learningNeuralNetBatchMode(Double[] error2Sum) {
        List<List<String>> preparedForLearningInputTable = neuralManager.getPreparedForLearningInputTable();

        List<List<List<String>>> batches = new ArrayList<>();
        createBatches(preparedForLearningInputTable, batches, batchSize);
        MathP.Counter counter = MathP.getCounter(1,1);
        int initialCountRow = counter.get();;
        for(int countBatch=0; countBatch<batches.size(); countBatch++) {

            List<String> initialRow =  batches.get(countBatch).get(0);
            Map<String, Double> initialRowInputFactor = getRowInputFactor(initialRow);
            // d[j]
            Map<String, Double> initialOutputFactorForLearning = rowOutputFactorForLearning(initialCountRow);
            // y[j]
            neuralModel.forwardPropagation(initialRowInputFactor);

            List<List<String>> batch = batches.get(countBatch);
            int batchSize = batch.size();
            List<NeuralModel> cloneNeuralModels = cloneNeuralModel(batchSize);

            List<Map<String, Double>> errorOutputFactorsForBatch = new ArrayList<>();

            for (int batchCount = 0; batchCount< batchSize; batchCount++) {

                List<String> row = batch.get(batchCount);
                Map<String, Double> rowInputFactor = getRowInputFactor(row);   // e[j] = d[j] - y[j]

                loggerFlagSetByCount(initialCountRow);
                Map<String, Double> rowOutputFactorForLearning = rowOutputFactorForLearning(initialCountRow);   // d[j]

                NeuralModel cloneNeuralModel = cloneNeuralModels.get(batchCount);

                Map<String, Double> outputFactorsRowCalculate
                        = cloneNeuralModel.forwardPropagation(rowInputFactor);   // y[j]

                Map<String, Double> errorOutputFactorsRow
                        = cloneNeuralModel.outputLayerNeuronErrors(rowOutputFactorForLearning, outputFactorsRowCalculate);   // e[j] = d[j] - y[j]

                NeuralModelService.backCalculateError(errorOutputFactorsRow, cloneNeuralModel.getLayers());
                errorOutputFactorsForBatch.add(errorOutputFactorsRow);
                initialCountRow = counter.get(); //neuralManager.getPreparedForLearningInputTable().indexOf(initialRow );

            }
            NeuralModelService.backCalculateDeltaWsBatchMode(neuralModel.getLayers(), cloneNeuralModels);

            Double error2 = 0.0;

            for (Map<String, Double> errorOutputFactorsRow: errorOutputFactorsForBatch) {
                for (double value : errorOutputFactorsRow.values()) {
                    error2 += value * value;
                }
            }


            error2Sum[0] += error2;
        }
    }

    private List<NeuralModel> cloneNeuralModel( int batchSize) {
        List<NeuralModel> cloneNeuralModels = new ArrayList<>();
        for (int i= 0; i<batchSize; i++) {
            try {
                NeuralModel cloneNeuralModel = neuralModel.clone();
                cloneNeuralModels.add(cloneNeuralModel);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                throw new NeuralNetElementCloneNotSupportedException("cloneNeuralModel " + getClass());
            }
        }
        return cloneNeuralModels;
    }

    private void createBatches(List<List<String>> preparedForLearningInputTable, List<List<List<String>>> batchs, int batchSize) {
        int countRow = 1; // !!! countRow=0 for the row, which consists of names of the columns, so the row with i=0 misses.
        int batchesSize = (preparedForLearningInputTable.size()-countRow) / batchSize;
        int modBatchesSize = (preparedForLearningInputTable.size()-countRow) % batchSize;

        for (int countBatch = 0; countBatch < batchesSize; countBatch++) {
            countRow = createBatch (batchs, batchSize, countRow);
        }
        createBatch (batchs, modBatchesSize, countRow);
    }

    @Deprecated
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

//    private Map<String, Double> rowOutputFactorForLearning(List<String> row) {
//        Map<String, Double> rowOutputFactorForLearning = new HashMap<>();
//        List<String> headerRow = neuralManager.getPreparedForLearningOutputTable().get(0);
//        for(int i=0; i<row.size(); i++) {
//            String key = headerRow.get(i).trim();
//            Double doubleValue = Double.parseDouble(row.get(i));
//            rowOutputFactorForLearning.put(key, doubleValue);
//        }
//        return rowOutputFactorForLearning;
//    }

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

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    private int createBatch (List<List<List<String>>> batchs, int batchSize, int countRow) {
        if (batchSize ==0) {
            return countRow;
        }
        int localCountRow =countRow;
        List<List<String>> batch = new ArrayList<>();
        for (int count = 0; count < batchSize; count++) {
            batch.add(preparedForLearningInputTable.get(localCountRow));
            localCountRow++;
        }
        batchs.add(batch);
        return localCountRow;
    }
}








