package neural.network.node.error.distribution;

import java.util.List;

interface NodeErrorDistribution {
    FunctionTx3<Double, Integer, Integer, List<List<Double>>> getDistribution();
}
