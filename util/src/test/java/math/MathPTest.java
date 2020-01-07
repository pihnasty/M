package math;

import org.junit.Test;

import java.util.List;

public class MathPTest {

    @Test
    public void counterTest() {

    int n = 10;

    MathP.Counter counter = MathP.getCounter(1,1);

    for (int i=0;i<n; i++ )  {
        System.out.println("i="+i+"     counter="+counter.get());
        }
    }


    @Test
    public void initArrayList() {
        List<List<Double>> listsDouble = MathP.initArrayList(0.0,5, 5);
        System.out.println(listsDouble);

        List<List<String>> listsString = MathP.initArrayList("1",5, 5);
        System.out.println(listsString);
    }
}
