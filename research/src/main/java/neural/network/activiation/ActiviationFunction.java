package neural.network.activiation;

import java.util.function.Function;

interface ActiviationFunction {

    Function<Double, Double> getFunction();
    Function<Double, Double> getDerivativeFunction();

}
