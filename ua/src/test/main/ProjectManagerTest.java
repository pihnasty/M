package main;

import math.MathP;
import neural.network.NeuralManager;
import neural.network.NeuralModel;
import neural.network.ws.Ws;
import neural.test.TransportSystem;
import org.junit.Test;
import settings.Settings;
import string.StringUtil;

import java.util.*;


public class ProjectManagerTest {

    @Test
    public void saveTableDataTest() {
        int additionSize = 2;


        ProjectManager projectManager = ProjectManager.getInstance();
        double deltaTau = 0.01;
        double tauMax = 100.0;
        int variant= 10;

        TransportSystem transportSystem = new TransportSystem(variant, deltaTau);



        List<List<String>> tableTest = new ArrayList<>();

        List<String> headear = new ArrayList<>();
        headear.add("   0.NN   ");
        transportSystem.getSections().forEach( section ->{
            headear.add(" "+(int)section.getName()+".input"+" ");
            headear.add(" "+(int)section.getName()+".speed"+" ");
            headear.add(" "+(int)section.getName()+".ksiD"+" ");
            headear.add(" "+(int)section.getName()+".density"+" ");
            headear.add(" "+(int)section.getName()+".output"+" ");
            headear.add(" "+(int)section.getName()+".delay"+" ");
            }
        );

        tableTest.add(headear);

        for (double tau = 0.0; tau < tauMax; tau += deltaTau) {
            double tau2 = tau;
            transportSystem.getRootSection().stream().forEach(
                section -> transportSystem.executeSection(transportSystem.getSectionByName(section.getName()), tau2)
            );
            List<String> row = new ArrayList<>();
            row.add(StringUtil.getDoubleFormatValue(tau2, headear.get(0).length() - additionSize));

            transportSystem.getSections().forEach(section -> {
                    row.add(StringUtil.getDoubleFormatValue(section.getInputValue(), headear.get(1).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(section.getSpeedValue(), headear.get(2).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(section.getKsi(), headear.get(3).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(section.getDensityValue(), headear.get(4).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(section.getOutputValue(), headear.get(5).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(section.getDelayValue(), headear.get(6).length() - additionSize));
                }
            );
            tableTest.add(row);
        }


        projectManager.saveData(tableTest, Settings.Values.NEURAL_NETWORk_MODEL_TEST_DATA_CSV);

    }

    /*
    * Billous Anna
    * */
    @Test
    public void learningNeuralNetTest() {
        NeuralModel neuralModel = new NeuralModel();
        NeuralManager neuralManager = NeuralManager.getManager();
        neuralManager.setNeuralModel(neuralModel);

        List<String> inputFactors = Arrays.asList("input1","input2","input3");
        List<String> outputFactors =  Arrays.asList("output1","output2","output3");

        Map<String, String> map2 = new HashMap<>();
        map2.put("count-node","3");
        map2.put("activation-function","1");
        map2.put("distribute-error" , "1");
        map2.put("optimization-method" , "5");
        map2.put("type-layer" , "head");

        Map<String, String> map3 = new HashMap<>();
        map3.put("count-node","3");
        map3.put("activation-function","1");
        map3.put("distribute-error" , "1");
        map3.put("optimization-method" , "5");
        map3.put("type-layer" , "tail");

        Map<String,  Map<String, String>> getPlanExperiment_getHiddenLayers = new HashMap<>();
        getPlanExperiment_getHiddenLayers.put("2",map2);
        getPlanExperiment_getHiddenLayers.put("3",map3);


       List<List<String>> separatedRawDataTable = new ArrayList<>();

        List<String> header = new ArrayList<>();
        header.addAll(inputFactors);
        header.addAll(outputFactors);
        List<String> doubleRow = Arrays.asList("0.9","0.1","0.8","1.9","2.0","3.0");

        separatedRawDataTable.add(header);
        separatedRawDataTable.add(doubleRow);


        neuralManager.buildArchitecture(
            inputFactors,
            getPlanExperiment_getHiddenLayers,
            outputFactors
        );

        Ws w1 = new Ws(3,3);
        List<List<Double>> wS1 = new ArrayList<>();
        List<Double> wS1_row1 = Arrays.asList(0.9,0.3,0.4);
        List<Double> wS1_row2 = Arrays.asList(0.2,0.8,0.2);
        List<Double> wS1_row3 = Arrays.asList(0.1,0.5,0.6);
        wS1.add(wS1_row1);
        wS1.add(wS1_row2);
        wS1.add(wS1_row3);
        w1.setListWs(wS1);
        neuralModel.getLayers().get(1).setW(w1);

        Ws w2 = new Ws(3,3);
        List<List<Double>> wS2 = new ArrayList<>();
        List<Double> wS2_row1 = Arrays.asList(0.3,0.7,0.5);
        List<Double> wS2_row2 = Arrays.asList(0.6,0.5,0.2);
        List<Double> wS2_row3 = Arrays.asList(0.8,0.1,0.9);
        wS2.add(wS2_row1);
        wS2.add(wS2_row2);
        wS2.add(wS2_row3);
        w2.setListWs(wS2);
        neuralModel.getLayers().get(2).setW(w2);


        neuralManager.prepareForLearningTable(inputFactors, outputFactors, separatedRawDataTable);

        neuralManager.learningNeuralNet();



        String formatStr ="        ";
        MathP.Counter counter = MathP.getCounter(1);
        neuralManager.getNeuralModel().getLayers().stream().forEach(
            layer -> {
                layer.getNodes().stream().forEach(
                    node->System.out.println(StringUtil.getDoubleFormatValue(node.getValue(),formatStr))
                );
                System.out.println("-----");
                if (counter.get() != 0) {
                    layer.getW().getListWs().stream().forEach(
                        wS -> {
                            wS.forEach( w -> System.out.print(StringUtil.getDoubleFormatValue(w, formatStr)));
                            System.out.println();
                        }
                    );
                }
                System.out.println("--error---");
                layer.getNodes().stream().forEach(
                    node->System.out.println(StringUtil.getDoubleFormatValue(node.getError(),formatStr))
                );

                System.out.println("==================================================");
            }
        );



    }



    /*

        "10" : {
      "count-node" : "5",
      "activation-function" : "1",
      "distribute-error" : "1",
      "optimization-method" : "5",
      "type-layer" : "head"
    },


    */


}