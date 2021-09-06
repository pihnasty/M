package neural.network.nodes;

import neural.network.common.NeuralNetworkConstant;
import neural.network.layers.Layer;

public class Node implements Cloneable {
    private final String factorName;
    private int id;
    private double value;
    private double error;

    public Node(int id, String factorName) {
        this.factorName = factorName.trim();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = (NeuralNetworkConstant.Factors.BIAS.equals(factorName)) ? 1.0 : value;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public String getFactorName() {
        return factorName;
    }

    @Override
    public Node clone() throws CloneNotSupportedException {
        Node cloneNode = (Node) super.clone();
        return cloneNode;
    }
}
