package neural.network.node.error.distribution;

import java.util.List;

interface NodeErrorDistribution {
    Function2dList2<Double, Integer, List<List<Double>>> getDistribution();
}
