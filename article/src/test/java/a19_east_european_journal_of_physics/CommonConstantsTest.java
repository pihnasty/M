package a19_east_european_journal_of_physics;

import org.testng.Assert;
import org.testng.annotations.Test;

import static a19_east_european_journal_of_physics.CommonConstants.v_1;

@Test
public class CommonConstantsTest {
    private Double exception =0.0001;

    @Test(enabled = true)
    public void v_fTest () {
        CommonConstants.Q_c = 0.5;
        CommonConstants.v_g = 4.0;
        CommonConstants.Q_r = 0.1;

        CommonConstants.v_b = 0.5;

        CommonConstants.v_f0 = 0.2;
        CommonConstants.v_f1 = 0.01;


        Assert.assertEquals(CommonConstants.v_f.apply(0.1), 0.201, exception);

        Assert.assertEquals(v_1* v_1, 16*3, exception);

        Double tau =0.1;
        Double v2 =CommonConstants.v_2.apply(tau);
        Assert.assertEquals(v2 *v2 ,
            CommonConstants.v_g*CommonConstants.v_g*(CommonConstants.v_f.apply(tau) +CommonConstants.v_b*(1.0+CommonConstants.Q_r/CommonConstants.Q_c) ), exception);


        System.out.println(v2/ v_1*v2/ v_1);


        System.out.println("CommonConstants.v_1="+ v_1);

    }

    @Test(enabled = true)
    public void v_dTest () {
        double v_1 = CommonConstants.v_1;
        double v_g = CommonConstants.v_g;
        double v_f1 = CommonConstants.v_f1;
        double v_d = - 1.0/(2.0*v_1)*v_g*v_g * v_f1 /v_1/v_1;


        System.out.println("v_1="+v_1);
        System.out.println("v_d="+v_d);

        double tau = 1.0;
        double ksi = 0.5;

        double z0= 2.0*v_d*v_1*tau*(ksi-1);

        System.out.println("z0="+z0);

    }
}