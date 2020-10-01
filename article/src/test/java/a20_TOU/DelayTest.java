package a20_TOU;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Test
public class DelayTest  {

    @Test(enabled = true)
    public void delayTestConstSpeed() {
        double dt = 0.01;
        List<Double> regularSpeeds = Arrays.asList(0.176, 0.878);
        double exception = 1.1* dt;
        Speed speed = new Speed(dt, regularSpeeds );
        Delay delay = new Delay(dt);

        double tau = 0;
        double speedValue;
        for (int i = 0; i < 1000; i++) {
            tau = i * dt;
            speedValue = 1;
            speed.add(tau, speedValue);
            delay.add(tau, speed);
            if (tau>1) {
                Assert.assertEquals(delay.getDeltaTau1(tau), 1.0, exception);
            }
        }
    }

    @Test(enabled = true)
    public void delayTestLinearSpeed() {
        double dt = 0.01;
        double exception = 1.1* dt;
        List<Double> regularSpeeds = Arrays.asList(0.176, 0.878);
        Speed speed = new Speed(dt, regularSpeeds);
        Delay delay = new Delay(dt);

        double tau = 0;
        double speedValue;
        for (int i = 0; i < 1000; i++) {
            tau = i * dt;
            speedValue = 1.0 + tau; // 1/6.28 + Math.sin(i*dt);
            speed.add(tau, speedValue);
            delay.add(tau, speed);
        }
        double tauExpected = 2.0;
        double tau1Expected = -1.0+Math.sqrt(7.0);
        Assert.assertEquals(delay.getDeltaTau1(tauExpected), tauExpected-tau1Expected, exception);

    }

    }