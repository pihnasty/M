package neural.network.node.error.distribution;

import neural.network.layers.Layer;

import java.util.List;

interface NodeErrorDistribution {
    static List<Double> calculatedErrorForHiddenLayer(Layer layerNext) {
        return null;
    }
}
