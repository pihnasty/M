package neural.test;

import neural.test.papameters.*;

import java.util.Arrays;
import java.util.function.Function;

public class ControlSpeedAndInputBunkerTransportSystem extends BaseTransportSystem {
    public ControlSpeedAndInputBunkerTransportSystem(int variant, double deltaTau) {
        super(variant, deltaTau);
    }

    protected void initTransportSystem() {
        M = 1;
        ksis = Arrays.asList(1.0);
        startIntegrationValue = 0.0;
        finishIntegrationValue = 1.0;
        initSections();
    }

    protected void initSections() {
        for (double name = 1.0; name <= M; name++) {
            int count = (int) (name - 1);
            inputs.add(new Input(name));
            psis.add(new Psi(name));
            outputPlans.add(new OutputPlan(name));
            inputBunkers.add(
                new InputBunker(
                    name
                    , tau -> inputs.get(count).getValue(tau)
                    , tau -> control_fromInputBunkers.get(count).getValue(tau)
                )
            );
            controlSpeeds.add(new ControlSpeed(
                    name
                    , tau -> outputPlans.get(count).getValue(tau)
                    , inputBunkers.get(count)
                )
            );

            control_fromInputBunkers.add(new Control_fromInputBunker(
                name
                , t -> controlSpeeds.get(count).getValue(t)
                , inputBunkers.get(count)
                )
            );

            sections.add(new Section(
                name
                , ksis.get(count)
                , startIntegrationValue
                , finishIntegrationValue
                , t -> control_fromInputBunkers.get(count).getValue(t)
                , t -> controlSpeeds.get(count).getValue(t)
                , t -> psis.get(count).getValue(t)
            ));

            controlSpeeds.forEach( controlSpeed -> {
                    controlSpeed.setSection(sections.get((int)controlSpeed.getName()-1));
                }
            );

            control_fromInputBunkers.forEach(control_fromInputBunker -> {
                    control_fromInputBunker.setControlSpeed(
                        controlSpeeds.get((int) control_fromInputBunker.getName() - 1)
                    );
                }
            );

        }
    }
}
