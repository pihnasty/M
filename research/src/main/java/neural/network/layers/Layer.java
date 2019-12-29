package neural.network.layers;

import neural.network.activiation.functions.ActiviationFunction;
import neural.network.activiation.functions.Ws;
import neural.network.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private ActiviationFunction activiationFunction;
    private final int id;
    private List<Node> nodes;
    private Layer previousLayer;
    private Ws wS;

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
}
