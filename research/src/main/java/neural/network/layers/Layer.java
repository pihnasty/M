package neural.network.layers;

import neural.network.NeuralModel;
import neural.network.activiation.functions.ActiviationFunction;
import neural.network.exceptions.NeuralNetElementCloneNotSupportedException;
import neural.network.nodes.Node;
import neural.network.optimization.method.OptimizationMethod;
import neural.network.ws.Ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Layer implements Cloneable {
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

    private void setNodes(List<Node> nodes) {
        this.nodes = nodes;
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

    private void setPreviousLayer(Layer previousLayer) {
        this.previousLayer = previousLayer;
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

    @Override
    public Layer clone() throws CloneNotSupportedException {
        Layer cloneLayer = (Layer) super.clone();

        List<Node> cloneNodes = nodes.stream().map(node-> {
            try {
                return node.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                throw new NeuralNetElementCloneNotSupportedException("CloneNotSupportedException "+getClass());
            }
        }).collect(Collectors.toList());
        cloneLayer.setNodes(cloneNodes);

        if (Objects.nonNull(cloneLayer.previousLayer)) {
            Layer clonePreviousLayer = previousLayer.clone();
            cloneLayer.setPreviousLayer(clonePreviousLayer);
        }

        Ws cloneWs;
        if (this instanceof InputLayer) {
            cloneWs = wS;
        } else {
            cloneWs = wS.clone();
        }
        cloneLayer.setW(cloneWs);

        return cloneLayer;
    }
}
