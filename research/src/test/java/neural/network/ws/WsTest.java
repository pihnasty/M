package neural.network.ws;


import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Test
public class WsTest {
    private Double exception =0.0001;


    @Test(enabled = true)
    public void randomTest(){
        Random random = new Random(1000);
        Assert.assertEquals(random.nextDouble(), 0.7101849056320707, exception);
    }

    @Test(enabled = true)
    public  void initWs () {
        Random random = new Random(1000);
        List<List<Double>> listWs = new ArrayList<>();
        for (int i1=0; i1 < 3; i1++) {
            List<Double> row = new ArrayList<>();
            for (int i2=0; i2 < 9; i2++) {
                row.add(random.nextDouble());
            }
            listWs.add(row);
        }
        System.out.println(listWs);
    }

}