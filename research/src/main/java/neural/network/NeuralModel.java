package neural.network;


import logging.LoggerP_test;
import math.MathP;
import math.linear.SolvingLinearSystems;
import neural.network.layers.Layer;
import neural.network.nodes.Node;
import neural.network.optimization.method.OptimizationMethod;
import neural.network.ws.Ws;
import string.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;




public class NeuralModel {
    private List<Layer> layers;


    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public Map<String, Double> forwardPropagation (Map<String, Double> inputFactorsRow) {

        List<Layer> layers = getLayers();
        List<Double> hiddenFactorsValues = new ArrayList<>();
        for (Layer layer: layers) {
            List<Node> nodes = layer.getNodes();
            if (layer.getId()==0) {
                for (int i=0; i<nodes.size(); i++) {
                    Node node = nodes.get(i);
                    node.setValue(inputFactorsRow.get(node.getFactorName()));
                }
            }
            if (layer.getId()>0 ) {
                List<Double> previousLayerHiddenFactorsValues = layer.getPreviousLayer().getNodes().stream().map(Node::getValue).collect(Collectors.toList());
                List<List<Double>> listWs = layer.getW().getListWs();
                List<Double> beforeActivationHiddenFactorsValues = SolvingLinearSystems.multiply(listWs,previousLayerHiddenFactorsValues);
                hiddenFactorsValues = beforeActivationHiddenFactorsValues.stream().map(
                    value->layer.getActiviationFunction().getFunction().apply(value)
                ).collect(Collectors.toList());
                for (int i=0; i<nodes.size(); i++) {
                    nodes.get(i).setValue(hiddenFactorsValues.get(i));
                }
            }
        }
        Map<String, Double> outputFactorsRowCalculate = new HashMap<>();
        layers.get(layers.size()-1).getNodes().forEach(
            node -> outputFactorsRowCalculate.put(node.getFactorName(),node.getValue())
        );
        return outputFactorsRowCalculate;
    }

    public Map<String, Double> forwardPropagationErrors(Map<String, Double> outputFactorsRowForLearning, Map<String, Double> outputFactorsRowCalculate) {
        Map<String, Double> errorOutputFactorsRow = new HashMap<>();
        outputFactorsRowForLearning.keySet().forEach(
            key -> errorOutputFactorsRow.put(key, outputFactorsRowForLearning.get(key) - outputFactorsRowCalculate.get(key))
        );
        return errorOutputFactorsRow;
    }

    public void backPropagation(Map<String, Double> errorOutputFactorsRow) {
        backCalculateError(errorOutputFactorsRow);
        backCalculateDeltaWs(errorOutputFactorsRow);
    }

    public void backCalculateError(Map<String, Double> errorOutputFactorsRow) {
        List<Layer> layers = getLayers();
        for (int i = layers.size() - 1; i > 0; i--) {
            Layer layer = layers.get(i);
            List<Node> nodes = layer.getNodes();
            if (i == layers.size() - 1) {
                for (int i2 = 0; i2 < nodes.size(); i2++) {
                    Node outputNode = nodes.get(i2);
                    String key =outputNode.getFactorName().trim();
                    outputNode.setError(errorOutputFactorsRow.get(key));
                }
            } else {
                Ws wsLayerIplus1 = layers.get(i+1).getW();
                Ws wsError = wsLayerIplus1.calculateWsError(layer.getDistributeErrorName(), wsLayerIplus1);
                layer.setWsError(wsError);

                List<Double> inputErrorValues = new ArrayList<>();
                List<Node> inputErrorNodes = layers.get(i + 1).getNodes();
                inputErrorNodes.forEach(node -> inputErrorValues.add(node.getError()));
                List<Double> errorHiddenLayers = SolvingLinearSystems.multiply(wsError.getListWs(), inputErrorValues);
                for (int i2 = 0; i2 < nodes.size(); i2++) {
                    nodes.get(i2).setError(errorHiddenLayers.get(i2));
                }
            }
        }
    }

    public void backCalculateDeltaWs(Map<String, Double> errorOutputFactorsRow) {
        List<Layer> layers = getLayers();
        for (int i = layers.size() - 1; i > 0; i--) {
            Layer layer = layers.get(i);
            List<Double> valueNodeFromPreviousLayers = layer.getPreviousLayer().getNodes().stream().map(Node::getValue).collect(Collectors.toList());

            Function<Double, Double> derivativeFunction = layer.getActiviationFunction().getDerivativeFunction("F(S)");
            List<Double> sigmaExpressions = layer.getNodes().stream().map(node->-node.getError()*derivativeFunction.apply(node.getValue())).collect(Collectors.toList());
       //     List<Double> sigmaExpressions = layer.getNodes().stream().map(node->-node.getError()*node.getValue()*(1.0-node.getValue())).collect(Collectors.toList());
            List<List<Double>> wlist = layer.getW().getListWs();

            List<List<Double>> gradientWlist =SolvingLinearSystems.multiplyColumnToRow(sigmaExpressions,valueNodeFromPreviousLayers);

            double alpha = layer.getAlpha();
            OptimizationMethod optimizationMethod = layer.getOptimizationMethod();
            List<List<Double>> deltaWlist = MathP.initArrayList(0.0,gradientWlist.size(), gradientWlist.get(0).size());
            for(int i1=0; i1<gradientWlist.size(); i1++) {
                for(int i2=0; i2<gradientWlist.get(0).size(); i2++) {
                    double gradientWlistValue = gradientWlist.get(i1).get(i2);
                    double wlistValue = wlist.get(i1).get(i2);
                    double optimizationAlpha = optimizationMethod.getOptimalSpeed(alpha, wlistValue, gradientWlistValue);
                    Double value = optimizationAlpha*gradientWlistValue;
                    deltaWlist.get(i1).set(i2,value);
                }
            }

            if(neural.network.NeuralManager.loggerFlag[0]) {
                logging(layer, wlist, deltaWlist);
            }

            //    Logger.getLogger(NeuralModel.class.getName());

            List<List<Double>> newWlist = SolvingLinearSystems.subtract(wlist,deltaWlist);
            layer.getW().setListWs(newWlist);
        }
        if(neural.network.NeuralManager.loggerFlag[0]) {
            logging( layers.get(0), null, null);
        }

    }

    private void logging(Layer layer, List<List<Double>> wlist, List<List<Double>> deltaWlist) {
        String columnSize = "Template        headerValue";
        String presition = ".14f";

        List<List<Double>> nodeValues = layer.getNodes().stream().map(node -> {
                List<Double> row = new ArrayList<>();
                row.add(node.getValue());
                row.add(node.getError());
                return row;
            }
        ).collect(Collectors.toList());


        String loggerTable =
        "level="+layer.getId()


            +"\nnodeValue and odeValueError"
            + StringUtil.getDoubleToFormatTable(nodeValues, columnSize, presition)

            +"\ndeltaWlistTable"
            +StringUtil.getDoubleToFormatTable(deltaWlist, columnSize, presition)
            +"\nwlistTable"
            + StringUtil.getDoubleToFormatTable(wlist, columnSize, presition);
        LoggerP_test.write(Level.INFO,loggerTable);
    }
}
