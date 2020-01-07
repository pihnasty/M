package a19_east_european_journal_of_physics;


import org.testng.annotations.Test;

@Test
public class W0_dKsiTest {
    private Double exception =0.0001;

    @Test(enabled = true)
    public void getValueTest() {

        W0_dKsi w0_dKsi = new W0_dKsi();


        w0_dKsi.getValue(1.0, 0.5);




    }

}
