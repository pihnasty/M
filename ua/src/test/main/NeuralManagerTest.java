package main;

import neural.network.NeuralManager;
import neural.network.ws.Ws;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class NeuralManagerTest {

    @Test
    public void showWsTest() {
        NeuralManager neuralManager = NeuralManager.getManager();

        String path = "D:\\A\\M\\MyProjects\\p71_Conveyor\\result\\neural_network_model\\learning_analysis_tool\\ws_serialize";
        String fileName = "epoch0001232000";

        String testingPath = "result\\neural_network_model\\learning_analysis_tool\\testing";
        String testingFileName = "ws_test_" + fileName + ".csv";

        List<Ws> wSs = neuralManager.deserializeNeuralNet(path, fileName);
        ProjectManager projectManager = ProjectManager.getInstance();

        List<List<String>> table = new ArrayList<>();
        WsReport wsReport = new WsReport();
        List<String> notationExampleRow = new ArrayList<>();

        notationExampleRow.add(wsReport.notationExample());

        table.add(notationExampleRow);

        addWsToTable(wSs, table, wsReport);
        projectManager.saveData(table,testingPath+"\\"+testingFileName);
    }

    private void addWsToTable(List<Ws> wSs, List<List<String>> table, WsReport wsReport) {
        wsReport.addWsToTable(wSs, table);
    }

}

class WsReport {

    public String notationExample() {
        String notation = "Notation Example:\n" +
            "----------------------------------------------------------------------------------------------------\n" +
            "| w11 w12 w13 w14 w15 |      | x1 |     | w11*x1 + w12*x2 + w13*x3 + w14*x4 + w15*x5 |     | z1 | \n" +
            "| w21 w22 w23 w24 w25 |   X  | x2 |  =  | w21*x1 + w22*x2 + w23*x3 + w24*x4 + w25*x5 |  =  | z2 | \n" +
            "| w31 w32 w33 w34 w35 |      | x3 |     | w31*x1 + w32*x2 + w33*x3 + w34*x4 + w35*x5 |     | z3 | \n" +
            "                             | x4 |                \n" +
            "                             | x5 |                \n" +
            "----------------------------------------------------------------------------------------------------";
        return notation;
    }

    public void addWsToTable(List<Ws> wSs, List<List<String>> table) {
        for (int hiddenLayerCounter=1; hiddenLayerCounter<wSs.size(); hiddenLayerCounter++) {
            List<String> hiddenLayerNameRow = new ArrayList<>();
            String hiddenLayerNumber;
            if(hiddenLayerCounter<wSs.size()-1) {
                hiddenLayerNumber = "Hidden layer: "+hiddenLayerCounter;
            } else {
                hiddenLayerNumber = "Output layer: "+hiddenLayerCounter;
            }

            hiddenLayerNameRow.add(hiddenLayerNumber);
            table.add(hiddenLayerNameRow);

            wSs.get(hiddenLayerCounter).getListWs().forEach(
                wSrow -> {
                    List<String> row = new ArrayList<>();
                    wSrow.forEach(value -> {
                        row.add(String.format(" %8.4f",value));
                    });
                    table.add(row);
                }
            );
        }
    }

}