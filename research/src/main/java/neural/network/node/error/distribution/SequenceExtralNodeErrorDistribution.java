package neural.network.node.error.distribution;


import math.linear.SolvingLinearSystems;
import neural.network.activiation.functions.ActiviationFunction;
import neural.network.layers.Layer;
import neural.network.nodes.Node;
import neural.network.ws.Ws;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SequenceExtralNodeErrorDistribution implements NodeErrorDistribution{

    public static List<Double> calculatedErrorForHiddenLayer(Layer layerNext) {
        List<Double> hiddenLayerErrors;

        List<Node> layerNextNodes = layerNext.getNodes();
        List<Double> value_x_derivativeFunctionValue = new ArrayList<>();

        ActiviationFunction activiationFunction = layerNext.getActiviationFunction();
        Function<Double, Double> derivativeFunction = activiationFunction.getDerivativeFunction("F(S)");

        value_x_derivativeFunctionValue = layerNextNodes.stream().map(
                nextNode->nextNode.getError()*derivativeFunction.apply(nextNode.getValue())).collect(Collectors.toList()
        );

        List<List<Double>> layerNextListWs = layerNext.getW().getListWs();
        List<List<Double>> transponeLayerListWs = SolvingLinearSystems.transponeMatrix(layerNextListWs);

        hiddenLayerErrors = SolvingLinearSystems.multiply(transponeLayerListWs, value_x_derivativeFunctionValue);
        return hiddenLayerErrors;
    }
}

