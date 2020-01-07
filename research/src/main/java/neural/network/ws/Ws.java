package neural.network.ws;

import math.linear.SolvingLinearSystems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Ws implements Serializable {

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

    public Ws (List<List<Double>> listWs) {
        this.numberNodes=listWs.size();
        this.numberNodesPreviousLauer = listWs.get(0).size();
        this.listWs = listWs;
    }


    public double getWs(int numberNodePreviousLauer, int numberNode) {
        return listWs.get(numberNodePreviousLauer).get(numberNode);
    }

    public void setW(int numberNodePreviousLauer, int numberNode, double value) {
        listWs.get(numberNodePreviousLauer).set(numberNode,value);
    }

    public Ws initWs () {
        Random random = new Random(1000);

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

    public Ws calculateWsError (String distributeErrorName, Ws wsLayer) {
        switch (distributeErrorName) {
            case "1" :
            case "proportional-value-w" :
                List<List<Double>> layerListWs = wsLayer.getListWs();
                List<Double> summaryValuesRow = SolvingLinearSystems.summaryValuesRow(layerListWs);
                List<Double> rowWithInverseValues =summaryValuesRow.stream().map(value->1.0/value).collect(Collectors.toList());

                List<List<Double>> proportionalListWs = SolvingLinearSystems.proportionalChangeMatrix(layerListWs,rowWithInverseValues);


                List<List<Double>> transponeLayerListWs = SolvingLinearSystems.transponeMatrix(proportionalListWs);

                Ws proportionalWs = new Ws(transponeLayerListWs);
                return proportionalWs;
                default: throw new ExceptionInInitializerError("1 - еру proportion to the weights Ws");

        }
    }

    public int getNumberNodesPreviousLauer() {
        return numberNodesPreviousLauer;
    }

    public int getNumberNodes() {
        return numberNodes;
    }
}
