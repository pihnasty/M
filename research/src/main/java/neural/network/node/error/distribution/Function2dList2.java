package neural.network.node.error.distribution;

@FunctionalInterface
public interface Function2dList2<R,T,L> {
    R apply(T p1, T p2, L p3);
}
