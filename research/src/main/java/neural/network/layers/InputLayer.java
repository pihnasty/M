package neural.network.layers;

import math.MathP;
import neural.network.common.NeuralNetworkConstant;
import neural.network.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class InputLayer extends Layer {

    public InputLayer(List<String> inputFactorNames) {
        super(0, null);
        MathP.Counter counter =MathP.getCounter(1);
        getNodes().add(new Node( counter.get(), NeuralNetworkConstant.Factors.BIAS));
        inputFactorNames.forEach(
            inputFactorName -> {
                getNodes().add(new Node( counter.get(), inputFactorName)); }
        );
    }
}
