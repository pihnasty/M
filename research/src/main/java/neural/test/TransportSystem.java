package neural.test;


import neural.test.papameters.Input;
import neural.test.papameters.Psi;
import neural.test.papameters.Section;
import neural.test.papameters.Speed;

import java.util.*;

public class TransportSystem extends BaseTransportSystem{

    public TransportSystem(int variant, double deltaTau) {
        super(variant, deltaTau);
    }

    protected void initTransportSystem () {
        M=8;
        ksis = Arrays.asList(1.0,0.5,0.7,0.8
                            ,1.5,1.0,1.5,0.6);

        startIntegrationValue=0.0;
        finishIntegrationValue=1.0;
        initSections();

        getSectionByName(1.0).setParents(Arrays.asList(getSectionByName(3.0)));
        getSectionByName(2.0).setParents(Arrays.asList(getSectionByName(3.0)));

        getSectionByName(3.0).setParents(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(4.0).setParents(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(5.0).setParents(Arrays.asList(getSectionByName(6.0)));

        getSectionByName(6.0).setParents(Arrays.asList(getSectionByName(7.0),getSectionByName(8.0)));


        getSectionByName(6.0).setOutputDistribution(
            new HashMap<Section,Double>() {
                {
                    put(getSectionByName(7.0),2.0);
                    put(getSectionByName(8.0),3.0);
                }
            }
        );

        getSectionByName(3.0).setChildren(Arrays.asList(getSectionByName(1.0),getSectionByName(2.0)));
        getSectionByName(6.0).setChildren(Arrays.asList(getSectionByName(3.0),getSectionByName(4.0),getSectionByName(5.0)));
        getSectionByName(7.0).setChildren(Arrays.asList(getSectionByName(6.0)));
        getSectionByName(8.0).setChildren(Arrays.asList(getSectionByName(6.0)));

    }

    protected void initSections() {
        for (double name=1.0; name<=M; name++) {
            int count = (int)(name-1);
            inputs.add(new Input(name));
            speeds.add(new Speed(name));
            psis.add(new Psi(name));

            sections.add(new Section(
                name
                ,ksis.get(count)
                ,startIntegrationValue
                ,finishIntegrationValue
                ,t->inputs.get(count).getValue(t)
                ,t->speeds.get(count).getValue(t)
                ,t->psis.get(count).getValue(t)
            ));
        }
    }

    public static void main(String[] args) {
        double tau = 0.0;
        TransportSystem transportSystem = new TransportSystem(1, deltaTau);

        transportSystem.getRootSection().stream().forEach(
            section -> transportSystem.executeSection(transportSystem.getSectionByName(section.getName()),tau)
        );
        System.out.println();

    }

}
