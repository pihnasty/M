package neural.network.optimization.method;

import java.util.function.Function;

public interface OptimizationMethod {

    Function<Double, Double> errorFunction = x -> { throw new ExceptionInInitializerError("The Optimization Method is not defined.");};

    double getOptimalSpeed (double alpha, double wlistValue, double gradientWlistValue);

}
