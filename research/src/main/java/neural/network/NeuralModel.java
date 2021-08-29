package neural.network;


import logging.LoggerP_test;
import math.MathP;
import math.linear.SolvingLinearSystems;
import neural.network.layers.Layer;
import neural.network.nodes.Node;
import neural.network.optimization.method.OptimizationMethod;
import neural.network.ws.Ws;
import settings.Settings;
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

    /**
     * The error signal of the j-th output neuron ( e[j]=d[j]-y[j]) for n-th example of the set.
     *
     * @param outputFactorsRowForLearning d[j]
     * @param outputFactorsRowCalculate   y[j]
     * @return set of the error e[j]
     */
    public Map<String, Double> outputLayerNeuronErrors(
            Map<String, Double> outputFactorsRowForLearning
            , Map<String, Double> outputFactorsRowCalculate) {
        Map<String, Double> errorOutputFactorsRow = new HashMap<>();
        outputFactorsRowForLearning.keySet().forEach(
                key -> errorOutputFactorsRow.put(
                        key
                        , outputFactorsRowForLearning.get(key) - outputFactorsRowCalculate.get(key)
                )
        );
        return errorOutputFactorsRow;
    }

}
