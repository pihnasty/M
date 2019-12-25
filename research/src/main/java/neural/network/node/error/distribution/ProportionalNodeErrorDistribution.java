package neural.network.node.error.distribution;


import java.util.List;

public class ProportionalNodeErrorDistribution implements NodeErrorDistribution{

    @Override
    public FunctionTx3<Double, Integer, Integer, List<List<Double>>> getDistribution() {
        return (p1,p2,p3) -> p3.get(p1).get(p2)/p3.get(p1).stream().reduce(0.0, Double::sum);
    }
}

