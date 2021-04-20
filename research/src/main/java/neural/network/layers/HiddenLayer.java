package neural.network.layers;



import neural.network.activiation.functions.FunctionManager;
import neural.network.nodes.HiddenNode;
import neural.network.optimization.method.OptimizationMethodManager;
import settings.Settings;

import java.util.Map;

public class HiddenLayer extends Layer {


    public HiddenLayer(int id, Map<String,String> extParameters,  Layer previousLayer ) {
        super(id, previousLayer);

        int countNode = Integer.parseInt(extParameters.get(settings.Settings.Keys.COUNT_NODE));
        String activationFunctionName = extParameters.get(Settings.Keys.ACTIVATION_FUNCTION);
        String distributeErrorName = extParameters.get(Settings.Keys.DISTRIBUTE_ERROR);
        Double alpha = Double.parseDouble(extParameters.get(Settings.Keys.ALPHA));
        String optimizationMethodName = extParameters.get(Settings.Keys.OPTIMIZATION_METHOD);
        boolean typeNode = Boolean.parseBoolean(extParameters.get(Settings.Keys.TYPE_LAYER));

        setActiviationFunction(FunctionManager.getFunction(activationFunctionName));
        setOptimizationMethod(OptimizationMethodManager.getOptimizationMethod(optimizationMethodName));
        setDistributeErrorName(distributeErrorName);
        setAlpha(alpha);

        for(Integer i=1; i<=countNode;i++) {
            getNodes().add(new HiddenNode(i,i.toString()));
        }
    }

}
