package neural.network;


import math.MathP;
import math.linear.SolvingLinearSystems;
import neural.network.ws.Ws;
import neural.network.layers.Layer;
import neural.network.nodes.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class NeuralModel {
    private List<Layer> layers;


    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public List<Double> forwardPropagation (List<Double> inputFactorsRow) {

        List<Layer> layers = getLayers();
        List<Double> hiddenFactorsValues = new ArrayList<>();
        for (Layer layer: layers) {
            List<Node> nodes = layer.getNodes();
            if (layer.getId()==0) {
                for (int i=0; i<nodes.size(); i++) {
                    nodes.get(i).setValue(inputFactorsRow.get(i));
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
        return hiddenFactorsValues;
    }

    public List<Double> forwardPropagationErrors(List<Double> outputFactorsRowForLearning, List<Double> outputFactorsRowCalculate) {
        List<Double> errorOutputFactorsRow = new ArrayList<>();
        for(int i=0; i <outputFactorsRowForLearning.size(); i++) {
            errorOutputFactorsRow.add(outputFactorsRowForLearning.get(i)-outputFactorsRowCalculate.get(i));
        }
        return errorOutputFactorsRow;

    }

    public void backPropagation(List<Double> errorOutputFactorsRow) {
        backCalculateError(errorOutputFactorsRow);
        backCalculateDeltaWs(errorOutputFactorsRow);
    }

    public void backCalculateError(List<Double> errorOutputFactorsRow) {
        List<Layer> layers = getLayers();
        for (int i = layers.size() - 1; i > 0; i--) {
            Layer layer = layers.get(i);
            List<Node> nodes = layer.getNodes();
            if (i == layers.size() - 1) {
                for (int i2 = 0; i2 < nodes.size(); i2++) {
                    nodes.get(i2).setError(errorOutputFactorsRow.get(i2));
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

    public void backCalculateDeltaWs(List<Double> errorOutputFactorsRow) {
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
            List<List<Double>> deltaWlist = MathP.initArrayList(0.0,gradientWlist.size(), gradientWlist.get(0).size());
            for(int i1=0; i1<gradientWlist.size(); i1++) {
                for(int i2=0; i2<gradientWlist.get(0).size(); i2++) {
                    Double value = alpha*gradientWlist.get(i1).get(i2);
                    deltaWlist.get(i1).set(i2,value);
                }
            }




            List<List<Double>> newWlist = SolvingLinearSystems.subtract(wlist,deltaWlist);
            layer.getW().setListWs(newWlist);
        }
    }
}
