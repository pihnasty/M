package neural.network.node.error.distribution;

import math.linear.SolvingLinearSystems;
import neural.network.layers.Layer;
import neural.network.nodes.Node;
import neural.network.ws.Ws;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceNodeErrorDistribution {

    public static List<Double> calculatedErrorForHiddenLayer(Layer layerNext) {
        String distributeErrorName = layerNext.getPreviousLayer().getDistributeErrorName();
        List<Double> errorHiddenLayers;

        switch (distributeErrorName) {
            case "1":
            case "proportional-value-w":
                errorHiddenLayers = ProportionalNodeErrorDistribution.calculatedErrorForHiddenLayer(layerNext);
                break;
            case "2":
            case "sequence-extra":
                errorHiddenLayers = SequenceExtralNodeErrorDistribution.calculatedErrorForHiddenLayer(layerNext);
                break;
            default:
                throw new ExceptionInInitializerError("1 - еру proportion to the weights Ws");
        }
        return errorHiddenLayers;
    }

}
