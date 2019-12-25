package a19_east_european_journal_of_physics;


import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ZTest {
    private Double exception =0.0001;

    @Test(enabled = true)
    public void getBettaTest() {

        Z z = new Z();

        double value = z.getBetta(2.2);
        System.out.println(value);
        Assert.assertEquals(z.getBetta(2.2), 0.2, exception);
        Assert.assertEquals(z.getBetta(-2.2), 0.2, exception);
        Assert.assertEquals(z.getBetta(1.2), 0.8, exception);
        Assert.assertEquals(z.getBetta(5.2), 0.8, exception);
        Assert.assertEquals(z.getBetta(-5.2), 0.8, exception);
        Assert.assertEquals(z.getBetta(0.0), 0.0, exception);
        Assert.assertEquals(z.getBetta(1.0), 1.0, exception);
        Assert.assertEquals(z.getBetta(-1.0), 1.0, exception);
        Assert.assertEquals(z.getBetta(2.0), 0.0, exception);
        Assert.assertEquals(z.getBetta(-2.0), 0.0, exception);

        System.out.println(z.getBetta(0.3-CommonConstants.v_1*0.1));
    }

}
