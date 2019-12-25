package neural.network.node.error.distribution;

@FunctionalInterface
public interface FunctionTx3<R,T1,T2,T3> {
    R apply(T1 p1, T2 p2, T3 p3);
}
