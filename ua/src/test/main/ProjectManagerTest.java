package main;

import neural.test.TransportSystem;
import org.junit.Test;
import settings.Settings;
import string.StringUtil;

import java.util.ArrayList;
import java.util.List;


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

}