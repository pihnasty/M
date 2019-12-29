package neural.network;


import math.linear.SolvingLinearSystems;
import neural.network.layers.Layer;
import neural.network.nodes.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class NeuralModel {
    private List<Layer> layers;


    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public void forwardPropagation (List<Double> inputFactorsValues, List<Double> outputFactorsValues) {

        List<Layer> layers = getLayers();
        List<Double> hiddenFactorsValues = new ArrayList<>();
        for (Layer layer: layers) {
            List<Node> nodes = layer.getNodes();
            if (layer.getId()==0) {
                for (int i=0; i<nodes.size(); i++) {
                    nodes.get(i).setValue(inputFactorsValues.get(i));
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
        outputFactorsValues = hiddenFactorsValues;

    }
}
