package neural.network.node.error.distribution;


import math.linear.SolvingLinearSystems;
import neural.network.layers.Layer;
import neural.network.nodes.Node;
import neural.network.ws.Ws;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProportionalNodeErrorDistribution implements NodeErrorDistribution{

    public static List<Double> calculatedErrorForHiddenLayer(Layer layerNext) {
        List<Double> errorHiddenLayers;
        List<List<Double>> layerListWs = layerNext.getW().getListWs();
        List<Double> summaryValuesRow = SolvingLinearSystems.summaryValuesRow(layerListWs);
        List<Double> rowWithInverseValues
                = summaryValuesRow.stream().map(value -> 1.0 / value).collect(Collectors.toList());

        List<List<Double>> proportionalListWs
                = SolvingLinearSystems.proportionalChangeMatrix(layerListWs, rowWithInverseValues);

        List<List<Double>> transponeLayerListWs = SolvingLinearSystems.transponeMatrix(proportionalListWs);

        Ws wsError = new Ws(transponeLayerListWs);

        List<Double> inputErrorValues = new ArrayList<>();
        List<Node> inputErrorNodes = layerNext.getNodes();
        inputErrorNodes.forEach(node -> inputErrorValues.add(node.getError()));
        errorHiddenLayers = SolvingLinearSystems.multiply(wsError.getListWs(), inputErrorValues);
        return errorHiddenLayers;
    }
}

