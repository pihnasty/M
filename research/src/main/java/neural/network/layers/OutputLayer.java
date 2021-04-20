package neural.network.layers;

import math.MathP;
import neural.network.activiation.functions.FunctionManager;
import neural.network.nodes.Node;
import neural.network.optimization.method.OptimizationMethodManager;
import settings.Settings;

import java.util.List;
import java.util.Map;


public class OutputLayer extends Layer {

    public OutputLayer(int id, Map<String,String> extParameters,
                       List<String> outputFactorNames, Layer previousLayer) {
        super(id, previousLayer);
        int countNode = Integer.parseInt(extParameters.get(settings.Settings.Keys.COUNT_NODE));
        String activationFunctionName = extParameters.get(Settings.Keys.ACTIVATION_FUNCTION);
        Double alpha = Double.parseDouble(extParameters.get(Settings.Keys.ALPHA));
        String distributeErrorName = extParameters.get(Settings.Keys.DISTRIBUTE_ERROR);
        String optimizationMethodName = extParameters.get(Settings.Keys.OPTIMIZATION_METHOD);
        boolean typeNode = Boolean.parseBoolean(extParameters.get(Settings.Keys.TYPE_LAYER));

        setActiviationFunction(FunctionManager.getFunction(activationFunctionName));
        setOptimizationMethod(OptimizationMethodManager.getOptimizationMethod(optimizationMethodName));
        setAlpha(alpha);


        MathP.Counter counter =MathP.getCounter(1);
        outputFactorNames.forEach(
            outputFactorName -> {
                getNodes().add(new Node( counter.get(), outputFactorName)); }
        );



    }
}
