package neural.network.activiation.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ws {

    private List<List<Double>> listWs;
    private int numberNodesPreviousLauer;
    private int numberNodes;

    public Ws (int numberNodesPreviousLauer, int numberNodes) {
        this.numberNodesPreviousLauer = numberNodesPreviousLauer;
        this.numberNodes=numberNodes;
        listWs = new ArrayList<>();
        for(int i1=0; i1<numberNodes; i1++) {
            List<Double> row = new ArrayList<>();
            for(int i2=0; i2<numberNodesPreviousLauer; i2++) {
                row.add(0.0);
            }
            listWs.add(row);
        }
    }


    public double getWs(int numberNodePreviousLauer, int numberNode) {
        return listWs.get(numberNodePreviousLauer).get(numberNode);
    }

    public void setW(int numberNodePreviousLauer, int numberNode, double value) {
        listWs.get(numberNodePreviousLauer).set(numberNode,value);
    }

    public Ws initWs () {
        Random random = new Random();

        for (int i1=0; i1 < numberNodes; i1++) {
            for (int i2=0; i2 < numberNodesPreviousLauer; i2++) {
                listWs.get(i1).set(i2,random.nextDouble());
            }
        }
        return this;
    }

    public List<List<Double>> getListWs() {
        return listWs;
    }

    public void setListWs(List<List<Double>> listWs) {
        this.listWs = listWs;
    }
}
