package a19_east_european_journal_of_physics;

import java.util.function.DoubleFunction;


public class CommonConstants {
    static public Double Q_c = 0.5;
    static public Double Q_r = 0.1;
    static public Double v_g = 5.0;

    static public Double v_b = 0.5;

    static public Double v_f0 = 0.1;
    static public Double v_f1 = 25.;
    static public DoubleFunction<Double> v_f = tau -> v_f0 + v_f1 * tau;


    static public Double v_1 = v_g * Math.sqrt((1+Q_c)/Q_c);
    static public DoubleFunction<Double>  v_2 = tau ->     v_g  * Math.sqrt (v_f .apply(tau)+v_b*(1+Q_r/Q_c));




    static public Double alfa1 = 0.5;

    static public Double w_01_Is = 1.0;
    static public Double z0_Is = 1.0;
    static public Double z1_Is = 1.0;
    static public Double z2_Is = 0.0;

}
