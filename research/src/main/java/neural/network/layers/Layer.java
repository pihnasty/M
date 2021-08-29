package neural.network.layers;

import neural.network.activiation.functions.ActiviationFunction;
import neural.network.nodes.Node;
import neural.network.optimization.method.OptimizationMethod;
import neural.network.ws.Ws;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private ActiviationFunction activiationFunction;
    private OptimizationMethod optimizationMethod;
    private double alpha;
    private final int id;
    private List<Node> nodes;
    private Layer previousLayer;
    private Ws wS;
    private String distributeErrorName;

    public Layer(int id, Layer previousLayer) {
        this.id = id;
        this.previousLayer = previousLayer;
        nodes = new ArrayList<>();
    }

    public void initRandomW ( ) {
        if (id!=0) {
            Ws wS = new Ws(previousLayer.nodes.size(), nodes.size());
            setW(wS.initWs());
        }
    }

    public int getId() {
        return id;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Ws getW() {
        return wS;
    }

    public void setW(Ws wS) {
        this.wS = wS;
    }

    public Layer getPreviousLayer() {
        return previousLayer;
    }

    public ActiviationFunction getActiviationFunction() {
        return activiationFunction;
    }

    public void setActiviationFunction(ActiviationFunction activiationFunction) {
        this.activiationFunction = activiationFunction;
    }


    public String getDistributeErrorName() {
        return distributeErrorName;
    }

    public void setDistributeErrorName(String distributeErrorName) {
        this.distributeErrorName = distributeErrorName;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public OptimizationMethod getOptimizationMethod() {
        return optimizationMethod;
    }

    public void setOptimizationMethod(OptimizationMethod optimizationMethod) {
        this.optimizationMethod = optimizationMethod;
    }
}
