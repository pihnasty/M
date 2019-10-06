package main;

import neural.test.ControlSpeedAndInputBunkerTransportSystem;
import neural.test.TransportSystem;
import org.junit.Test;
import settings.Settings;
import string.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class ProjectManagerControlSpeedInputBunkerTest {

    @Test
    public void saveTableDataTest() {
        int additionSize = 2;


        ProjectManager projectManager = ProjectManager.getInstance();
        double deltaTau = 0.01;
        double tauMax = 100.0;
        int variant= 16;

        ControlSpeedAndInputBunkerTransportSystem transportSystem = new ControlSpeedAndInputBunkerTransportSystem(variant, deltaTau);



        List<List<String>> tableTest = new ArrayList<>();

        List<String> headear = new ArrayList<>();
        headear.add("   0.NN   ");


        transportSystem.getInputBunkers().forEach( inputBunker -> {
            headear.add(" "+(int)inputBunker.getName()+".capacity"+" ");
            headear.add(" "+(int)inputBunker.getName()+".capacityMax"+" ");
            headear.add(" "+(int)inputBunker.getName()+".capacityMin"+" ");
            }
        );

        transportSystem.getControl_fromInputBunkers().forEach(control_fromInputBunker -> {
                headear.add(" " + (int) control_fromInputBunker.getName() + ".control_fromInputBunker" + " ");
                headear.add(" " + (int) control_fromInputBunker.getName() + ".control_fromInputBunkerMax" + " ");
                headear.add(" " + (int) control_fromInputBunker.getName() + ".control_fromInputBunkerMin" + " ");
                headear.add(" " + (int) control_fromInputBunker.getName() + ".densityValueMax" + " ");
            }
        );

        transportSystem.getInputs().forEach(input -> {
                headear.add(" " + (int) input.getName() + ".input" + " ");
            }
        );

        transportSystem.getControlSpeeds().forEach(controlSpeed -> {
                headear.add(" " + (int) controlSpeed.getName() + ".controlSpeed" + " ");
                headear.add(" " + (int) controlSpeed.getName() + ".controlSpeedMax" + " ");
                headear.add(" " + (int) controlSpeed.getName() + ".controlSpeedMin" + " ");
            }
        );

        transportSystem.getOutputPlans().forEach( outputPlan -> {
            headear.add(" " + (int) outputPlan.getName() + ".outputPlan" + " ");
            }
        );

        transportSystem.getSections().forEach( section ->{
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

            transportSystem.getInputBunkers().forEach(inputBunker ->  {
                inputBunker.execute(tau2);
            });

            transportSystem.getInputBunkers().forEach(inputBunker ->  {
                row.add(StringUtil.getDoubleFormatValue(inputBunker.getCapacity(), headear.get(1).length() - additionSize));
                row.add(StringUtil.getDoubleFormatValue(inputBunker.getCapacityMax(), headear.get(2).length() - additionSize));
                row.add(StringUtil.getDoubleFormatValue(inputBunker.getCapacityMin(), headear.get(3).length() - additionSize));
                }
            );

            transportSystem.getControl_fromInputBunkers().forEach(control_fromInputBunker ->  {
                    row.add(StringUtil.getDoubleFormatValue(control_fromInputBunker.getValue(tau2), headear.get(4).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(control_fromInputBunker.getControl_fromInputBunkerValueMax(), headear.get(5).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(control_fromInputBunker.getControl_fromInputBunkerValueMin(), headear.get(6).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(control_fromInputBunker.getDensityValueMax(), headear.get(7).length() - additionSize));
                }
            );

            transportSystem.getInputs().forEach(input ->  {
                    row.add(StringUtil.getDoubleFormatValue(input.getValue(tau2), headear.get(8).length() - additionSize));
                }
            );

            transportSystem.getControlSpeeds().forEach(controlSpeed ->  {
                    row.add(StringUtil.getDoubleFormatValue(controlSpeed.getValue(tau2), headear.get(9).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(controlSpeed.getControlSpeedValueMax(), headear.get(10).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(controlSpeed.getControlSpeedValueMin(), headear.get(11).length() - additionSize));
                }
            );

            transportSystem.getOutputPlans().forEach(outputPlan ->  {
                    row.add(StringUtil.getDoubleFormatValue(outputPlan.getValue(tau2), headear.get(12).length() - additionSize));
                }
            );

            transportSystem.getSections().forEach(section -> {
                    row.add(StringUtil.getDoubleFormatValue(section.getDensityValue(), headear.get(13).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(section.getOutputValue(), headear.get(14).length() - additionSize));
                    row.add(StringUtil.getDoubleFormatValue(section.getDelayValue(), headear.get(15).length() - additionSize));
                }
            );


            tableTest.add(row);
        }


        projectManager.saveData(tableTest, Settings.Values.NEURAL_NETWORk_MODEL_SPEED_INPUT_TEST_DATA_CSV);

    }

}