package neural.network.ws;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

@Test
public class WsTest {
    private Double exception =0.0001;


    @Test(enabled = true)
    public void randomTest(){
        Random random = new Random(1000);
        Assert.assertEquals(random.nextDouble(), 0.7101849056320707, exception);
    }

}