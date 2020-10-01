package a20_TOU;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TariffTest {

    @Test
    public void testGetMaxValue() {
        double dt = 0.01;
        Tariff tariff = Tariff.Ukraine3zone;
        Assert.assertEquals(tariff.getMax(dt) , 1.8, dt);
    }

    @Test
    public void testGetMinValue() {
        double dt = 0.01;
        Tariff tariff = Tariff.Ukraine3zone;
        Assert.assertEquals(tariff.getMin(dt) , 0.25, dt);
    }
}