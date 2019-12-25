package a19_east_european_journal_of_physics;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class Z0Test {
    private Double exception =0.0001;

    @Test(enabled = true)
    public void getBettaTest() {

        Z0 z = new Z0();

        double ksi = 0.3;
        double tau = 0.1;
        double v1 = CommonConstants.v_1;

        double b1 = ksi+v1*tau;
        double b2 = ksi-v1*tau;

        Assert.assertEquals(z.getValue(b1, b2), 0.0, exception);


        for (double d=0; d<1000; d+=0.01) {
            Assert.assertEquals(z.getValue(1+d, 11.3-d), 0.0, exception);
            System.out.println(d);
        }




    }

}

