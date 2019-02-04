package math;

import org.junit.Test;

public class MathPTest {

    @Test
    public void counterTest() {

    int n = 10;

    MathP.Counter counter = MathP.getCounter(1,1);

    for (int i=0;i<n; i++ )  {
        System.out.println("i="+i+"     counter="+counter.get());
        }

    }
}
