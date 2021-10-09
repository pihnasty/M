package neural.network;

import logging.LoggerP_test;
import math.MathP;
import math.linear.SolvingLinearSystems;
import neural.network.layers.Layer;
import neural.network.node.error.distribution.ServiceNodeErrorDistribution;
import neural.network.nodes.Node;
import neural.network.optimization.method.OptimizationMethod;
import neural.network.ws.Ws;
import string.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class NeuralModelService {
    public static void backCalculateError(Map<String, Double> errorOutputFactorsRow, List<Layer> layers) {
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
                List<Double> hiddenLayerErrors
                        = ServiceNodeErrorDistribution.calculatedErrorForHiddenLayer(layers.get(i + 1));
                for (int i2 = 0; i2 < nodes.size(); i2++) {
                    nodes.get(i2).setError(hiddenLayerErrors.get(i2));
                }
            }
        }
    }

    public static void backCalculateDeltaWsSequentialMode(List<Layer> layers) {
        for (int i = layers.size() - 1; i > 0; i--) {

            Layer layer = layers.get(i);
            //     List<Double> sigmaExpressions = layer.getNodes().stream().map(node->-node.getError()*node.getValue()*(1.0-node.getValue())).collect(Collectors.toList());
            List<List<Double>> wlist = layer.getW().getListWs();
            List<List<Double>> gradientWlist = gradientWlist(layer);

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

    public static void backCalculateDeltaWsBatchMode(List<Layer> layers, List<NeuralModel> cloneNeuralModels) {

        for (int i = layers.size() - 1; i > 0; i--) {

            Layer layer = layers.get(i);
            //     List<Double> sigmaExpressions = layer.getNodes().stream().map(node->-node.getError()*node.getValue()*(1.0-node.getValue())).collect(Collectors.toList());

            List<List<Double>> wlist = layer.getW().getListWs();
            List<List<Double>> gradientWlist = new ArrayList<>();

            for (NeuralModel cloneNeuralModel : cloneNeuralModels) {
                Layer cloneLayer = cloneNeuralModel.getLayers().get(i);
            //    List<List<Double>> clone_wlist = cloneLayer.getW().getListWs();
                List<List<Double>> cloneGradientWlist = gradientWlist(cloneLayer);
                if(gradientWlist.isEmpty()) {
                    gradientWlist = cloneGradientWlist;
                } else {
                    gradientWlist = SolvingLinearSystems.add(gradientWlist, cloneGradientWlist);
                }
            }

            gradientWlist = SolvingLinearSystems.multiply(1.0 / (double) cloneNeuralModels.size(), gradientWlist);

            double alpha = layer.getAlpha();
            OptimizationMethod optimizationMethod = layer.getOptimizationMethod();
            double optimizationAlpha;
            List<List<Double>> deltaWlist = MathP.initArrayList(0.0,gradientWlist.size(), gradientWlist.get(0).size());



            for(int i1=0; i1<gradientWlist.size(); i1++) {
                for(int i2=0; i2<gradientWlist.get(0).size(); i2++) {
                    double gradientWlistValue = gradientWlist.get(i1).get(i2);
                    double wlistValue = wlist.get(i1).get(i2);
                    optimizationAlpha = optimizationMethod.getOptimalSpeed(alpha, wlistValue, gradientWlistValue);
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


    private static List<List<Double>> gradientWlist(Layer layer) {
        List<Double> valueNodeFromPreviousLayers = layer.getPreviousLayer().getNodes().stream().map(Node::getValue).collect(Collectors.toList());
        Function<Double, Double> derivativeFunction = layer.getActiviationFunction().getDerivativeFunction("F(S)");
        List<Double> expressions = layer.getNodes().stream().map(node->-node.getError()*derivativeFunction.apply(node.getValue())).collect(Collectors.toList());
        List<List<Double>> gradientWlist =SolvingLinearSystems.multiplyColumnToRow(expressions,valueNodeFromPreviousLayers);
        return gradientWlist;
    }

    private static void logging(Layer layer, List<List<Double>> wlist, List<List<Double>> deltaWlist) {
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


                        +"\nnodeValue and nodeValueError"
                        + StringUtil.getDoubleToFormatTable(nodeValues, columnSize, presition)

                        +"\ndeltaWlistTable"
                        +StringUtil.getDoubleToFormatTable(deltaWlist, columnSize, presition)
                        +"\nwlistTable"
                        + StringUtil.getDoubleToFormatTable(wlist, columnSize, presition);
        LoggerP_test.write(Level.INFO,loggerTable);
    }
}
