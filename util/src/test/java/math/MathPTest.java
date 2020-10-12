package math;

import org.junit.Test;

import java.util.ArrayList;
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

    @Test
    public void testBinarySearch() {
        List<Double> list = new ArrayList<>();
        for (int i =1; i<1000; i++) {
            list.add(0.01*i*i);
        }

        int i = MathP.binarySearch(list, 0.01 * 100 * 100 + 0.0000001, 0, list.size());

        System.out.println( list.get(99));
        System.out.println( list.get(100));
        System.out.println( list.get(101));
        System.out.println(i);
    }
}
