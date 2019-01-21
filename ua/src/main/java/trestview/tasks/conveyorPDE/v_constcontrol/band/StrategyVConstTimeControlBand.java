package trestview.tasks.conveyorPDE.v_constcontrol.band;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.stream.Collectors;

public interface StrategyVConstTimeControlBand {

    double decision(double s, double t);

    double getControlConstantSpeedBand(double t);

    double getSigma(double t);

    double decisionCharacteristic(double s, double t);

    AxisParametrs getAxisParametrs();

    void createCash();

    /*Parameters for the graph scale t={tMin,tMax}, S={sMin,sMax}, y={yMin,yMax}
    * numberOfCurves - Number of curves on the graph (number of graphs)
    * yTickUnitNumber - Number of the tick at the axis */
    class AxisParametrs {

        private double tMax;
        private double tMin;
        private double sMax;
        private double sMin;
        private double yMax;
        private double yMin;
        private double numberOfCurves;
        private double yTickUnitNumber;


        public AxisParametrs() {
            this(0.0, 1.0, 0.0, 2.0, 0.0, 1.0, 10.0, 10.0);
        }

        public AxisParametrs(double numberOfCurves, double yTickUnitNumber) {
            this(0.0, 1.0, 0.0, 1.0, 0.0, 1.0, numberOfCurves, yTickUnitNumber);
        }

        public AxisParametrs(double tMin, double tMax, double sMin, double sMax, double yMin, double yMax, double numberOfCurves, double yTickUnitNumber) {
            this.tMin = tMin;
            this.tMax = tMax;
            this.sMin = sMin;
            this.sMax = sMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.numberOfCurves = numberOfCurves;
            this.yTickUnitNumber = yTickUnitNumber;
        }


        public double gettMax() {
            return tMax;
        }

        public void settMax(double tMax) {
            this.tMax = tMax;
        }

        public double gettMin() {
            return tMin;
        }

        public void settMin(double tMin) {
            this.tMin = tMin;
        }

        public double getsMax() {
            return sMax;
        }

        public void setsMax(double sMax) {
            this.sMax = sMax;
        }

        public double getsMin() {
            return sMin;
        }

        public void setsMin(double sMin) {
            this.sMin = sMin;
        }

        public double getyMax() {
            return yMax;
        }

        public void setyMax(double yMax) {
            this.yMax = yMax;
        }

        public double getyMin() {
            return yMin;
        }

        public void setyMin(double yMin) {
            this.yMin = yMin;
        }

        public double getNumberOfCurves() {
            return numberOfCurves;
        }

        public void setNumberOfCurves(double numberOfCurves) {
            this.numberOfCurves = numberOfCurves;
        }

        public double getyTickUnitNumber() {
            return yTickUnitNumber;
        }

        public void setyTickUnitNumber(double yTickUnitNumber) {
            this.yTickUnitNumber = yTickUnitNumber;
        }

        public double getYtickUnit() {
            return (yMax - yMin) / yTickUnitNumber;
        }

    }

    /*
    * s0, t0 - The coordinate of the technological position and the time when the subjects of labour began to come in at this position
    * tS= Ts/Td, - Ratio of the duration of the works day to the duration of the production cycle
    * tSigma - The period of the fluctuating demand for the products
    * */
    class TaskParameters {

        protected double s0;
        protected double t0 = 0.0;
        protected double tK = new AxisParametrs().tMax;
        protected double tS;
        protected double tSigma;

        public TaskParameters() {
        }

        public TaskParameters(double t0, double tK, double tS,  double tSigma) {
            this(t0, tK, tS, tSigma, 0.0);
        }

        public TaskParameters(double t0, double tK, double tS,  double tSigma, double s0) {
            this.tS = tS;
            this.t0 = t0;
            this.tK = tK;
            this.s0 = s0;
            this.tSigma = tSigma;

        }

        public double getS0() {
            return s0;
        }

        public void setS0(double s0) {
            this.s0 = s0;
        }

        public double getT0() {
            return t0;
        }

        public void setT0(double t0) {
            this.t0 = t0;
        }

        public double gettK() {
            return tK;
        }

        public void settK(double tK) {
            this.tK = tK;
        }

        public double gettS() {
            return tS;
        }

        public void settS(double tS) {
            this.tS = tS;
        }
    }

    class Psi {
        protected DoubleFunction<Double> hm1_S = s -> MathP.h(s) * (1 - s);
        protected DoubleFunction<Double> hm_1_plus_sinS = s -> (1.0 + MathP.h(s) * Math.sin(10 * Math.PI * s)) / 2.0;
    }

    /*
     * wSigma = 2Pi/tSigma,  The Circular frequency of the fluctuating demand for the products
     * */
    class Sigma {
        private TaskParameters taskParameters;
        private double wSigma;
        protected DoubleFunction<Double> p2_plus_cosWsigmaT ;

        public Sigma(TaskParameters taskParameters) {
            this.taskParameters = taskParameters;
            this.wSigma = 2.0 * Math.PI / taskParameters.tSigma;
            p2_plus_cosWsigmaT = tau ->  (1.0 + Math.sin( this.wSigma  * tau));
        }

    }

    class ControlSpeedBand {

        private TaskParameters taskParameters;
        private List<Pair<Double, Double>> constantSpeedBand;
        private double initialControlSpeedBand;
        private double [] cascadeControlSpeedBand ={1.0};

        public ControlSpeedBand(TaskParameters taskParameters) {
            this.taskParameters = taskParameters;
            initialControlSpeedBand = cascadeControlSpeedBand[0];
        }
        public ControlSpeedBand() {
            this(new TaskParameters());
        }

        public List<Pair<Double, Double>> calculateConstantSpeedBand(DoubleFunction<Double> sigma, DoubleFunction<Double> gamma) {
            constantSpeedBand = new ArrayList<>();
            double dt = (taskParameters.gettK()-taskParameters.getT0())/MathP.NUMBER_AXIS_PARTITION;
            double t1Start = -1.0/initialControlSpeedBand;
            double t1=t1Start;
 //            System.out.printf("%-12s%-12s%-12s%-12s%-12s%-12s%s\n","t","t1","u","u1","sigma","t-t1","u_calculate");
            for (double t = t1Start; t < taskParameters.gettK(); t += dt) {
                double u;
                if (t < 0.0) {
                    u=initialControlSpeedBand;
                } else {
                   double u_t1 = getConstantSpeedBand(t1, t1Start,  dt);
                    u =  optimalControlSpeedBand ( u_t1*sigma.apply(t)/gamma.apply(t1));

                //    if(t>5.0) u= 1.0;

                   t1= t1+dt*u/u_t1;
//                   if (0.9<t && t<2.9)
//                       System.out.printf("%-12.5f%-12.5f%-12.5f%-12.5f%-12.5f%-12.5f%-12.5f\n",t,t1,u,u_t1 ,sigma.apply(t),t-t1, u_t1*sigma.apply(t)/gamma.apply(t1));

                }
                constantSpeedBand.add(new Pair<>(t, u));
            }
            return constantSpeedBand;
        }

        private double getConstantSpeedBand(double t, double tStart, double dt) {
            int i = (int) ((t-tStart)/dt);
            i = (i>=0) ? i : 0;
            i = (i<constantSpeedBand.size()) ? i : constantSpeedBand.size()-1;
            return constantSpeedBand.get(i).getValue();
        }

        public double optimalControlSpeedBand(Double controlSpeedBand) {
            class Result {
                double optimalControlSpeedBandResult = 0.0;
                double delta = Double.MAX_VALUE;
                List<Double> mas = new ArrayList<>();

                public Result(double[] doubleMas) {
                    for (Double m : cascadeControlSpeedBand) {
                        mas.add(m);
                    }
                }
            }
            Result r = new Result(cascadeControlSpeedBand);

            r.mas.stream().forEach(speed -> {
                        if (Math.abs(speed - controlSpeedBand) < r.delta) {
                            r.delta = Math.abs(speed - controlSpeedBand);
                            r.optimalControlSpeedBandResult = speed;
                        }
                    }
            );
            return r.optimalControlSpeedBandResult;
        }



        public double[] getCascadeControlSpeedBand() {
            return cascadeControlSpeedBand;
        }

        public ControlSpeedBand setCascadeControlSpeedBand(double[] cascadeControlSpeedBand) {
            this.cascadeControlSpeedBand = cascadeControlSpeedBand;
            return this;
        }

        public double getInitialControlSpeedBand() {
            return initialControlSpeedBand;
        }

        public ControlSpeedBand setInitialControlSpeedBand(double initialControlSpeedBand) {
            this.initialControlSpeedBand = initialControlSpeedBand;
            return this;
        }
    }


    class G {
        private TaskParameters taskParameters;
        protected DoubleFunction<Double> controlConstantSpeedBand;
        private List<Pair<Double, Double>> controlConstantSpeedBandList;

        public G() {
            this(new TaskParameters());
        }

        public G(TaskParameters taskParameters) {
            this.taskParameters = taskParameters;
            controlConstantSpeedBand = t -> {
                double t0 = controlConstantSpeedBandList.get(0).getKey();
                double dt = controlConstantSpeedBandList.get(1).getKey() - t0;
                int i = (int) (( t - t0)/dt);
                i = (i>=0) ? i : 0;
                i = (i<controlConstantSpeedBandList.size()) ? i : controlConstantSpeedBandList.size()-1;
                return controlConstantSpeedBandList.get(i).getValue();
            };
        }

        public G setControlConstantSpeedBandList(List<Pair<Double, Double>> controlConstantSpeedBandList) {
            this.controlConstantSpeedBandList = controlConstantSpeedBandList;
            return this;
        }
    }

    class Gamma {
        protected DoubleFunction<Double> h = t -> MathP.h(t);
        protected DoubleFunction<Double> p1 = t -> 1.0;
    }

    class CashIntegralValue {
        private List<Pair<Double, Double>> integralCashSortedValue;
        private List<Pair<Double, Double>> integralCashSortedKey;
        private double C1max = Double.MIN_VALUE;
        private double C1min = Double.MAX_VALUE;
        private double tMax = Double.MIN_VALUE;
        private double tMin = Double.MAX_VALUE;


        public CashIntegralValue() {
            integralCashSortedValue = new ArrayList<>();
            integralCashSortedKey = new ArrayList<>();
        }

        public void addValue(double t, double C1) {
            integralCashSortedKey.add(new Pair<>(t, C1));
        }

        public void sortedValue() {
            integralCashSortedValue = integralCashSortedKey.stream().sorted((p1, p2) -> (p2.getValue() > p1.getValue()) ? -1 : 1)
                    .collect(Collectors.toList());
            integralCashSortedKey = integralCashSortedKey.stream().sorted((p1, p2) -> (p2.getKey() > p1.getKey()) ? -1 : 1)
                    .collect(Collectors.toList());
            if (!integralCashSortedValue.isEmpty()) {
                C1max = integralCashSortedValue.get(integralCashSortedValue.size() - 1).getValue();
                C1min = integralCashSortedValue.get(0).getValue();
                tMax = integralCashSortedKey.get(integralCashSortedKey.size() - 1).getKey();
                tMin = integralCashSortedKey.get(0).getKey();
            }
        }

        public double getKey(double C1) {
            int i = integralCashSortedValue.size() / 2;
            try {
                if (C1 > C1max)
                    C1 = C1max;  // throw new Exception("-------------------- C1 > C1max ---------------------  C1="+C1+"  C1Max="+C1max);
                if (C1 < C1min)
                    C1 = C1min;  // throw new Exception("-------------------- C1 < C1min ---------------------  C1="+C1+"  C1Min="+C1min);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return methodNewtonValue(0, integralCashSortedValue.size() - 1, C1);   //  return integralCashSorted.stream().filter(p -> p.getValue() > C1).findFirst().get().getKey();
        }

        public double methodNewtonValue(int iMin, int iMax, double C1) {
            int i = (iMax + iMin) / 2;
            if (C1_isMore_Ci(C1, i)) iMin = i;
            else iMax = i;
            if ((iMax - iMin) > 2) return methodNewtonValue(iMin, iMax, C1);
            else return integralCashSortedValue.get(i).getKey();
        }

        public boolean C1_isMore_Ci(double C1, int i) {
            return C1 > integralCashSortedValue.get(i).getValue();
        }

        public double getValue(double t) {
            int i = integralCashSortedKey.size() / 2;
            try {
                if (t > tMax)
                    t = tMax;  // throw new Exception("-------------------- C1 > C1max ---------------------  C1="+C1+"  C1Max="+C1max);
                if (t < C1min)
                    t = tMin;  // throw new Exception("-------------------- C1 < C1min ---------------------  C1="+C1+"  C1Min="+C1min);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return methodNewtonKey(0, integralCashSortedKey.size() - 1, t);   //  return integralCashSorted.stream().filter(p -> p.getValue() > C1).findFirst().get().getKey();
        }

        public double methodNewtonKey(int iMin, int iMax, double t) {
            int i = (iMax + iMin) / 2;
            if (t_isMore_ti(t, i)) iMin = i;
            else iMax = i;
            if ((iMax - iMin) > 2) return methodNewtonKey(iMin, iMax, t);
            else return integralCashSortedKey.get(i).getValue();
        }

        public boolean t_isMore_ti(double t, int i) {
            return t > integralCashSortedKey.get(i).getKey();
        }
    }

    class MathP {

        static final double NUMBER_AXIS_PARTITION = 1000;

        static double h(double x) {
            return (x == 0.0) ? 0.5 : ((x > 0) ? 1.0 : 0.0);
            //return ( x>=0.0) ? 1.0 : 0.0;
        }

        static double h(double x, String s) {
            if ("1.0".equals(s)) return (x >= 0.0) ? 1.0 : 0.0;
            else return (x == 0.0) ? 0.5 : ((x > 0) ? 1.0 : 0.0);
        }

        static FourDoubleFunction integralF = (t0, tK, dt, function) -> {

            double result = 0.0;
            for (double t = t0; t < tK; t += dt) {
                result = result + function.apply(t) * dt;
            }
            return result;
        };

        static FiveDoubleFunction integralForCashSorted = (t0, tK, dt, function, integralCashSorted) -> {

            double result = 0.0;
            for (double t = t0; t < tK; t += dt) {
                result = result + function.apply(t) * dt;
                integralCashSorted.addValue(t, result);
            }
        };

        static FourDoubleFunction integrationParameter =
                (t0, tK, C1, function) -> {
                    if (C1 <= 0) return t0;

                    double dt = (tK - t0) / NUMBER_AXIS_PARTITION;
                    double result = 0.0;
                    double tP = t0;

                    while (result < C1) {
                        result = integralF.apply(t0, tP, dt, function);
                        tP += dt;
                    }
                    return tP;
                };

        interface FourDoubleFunction {
            Double apply(double t0, double tK, double С1, DoubleFunction<Double> function);
        }

        interface FiveDoubleFunction {
            void apply(double t0, double tK, double dt, DoubleFunction<Double> function, CashIntegralValue integralCashSorted);
        }
    }
}

class AbstractStrategyVConstTimeControlBand implements StrategyVConstTimeControlBand {
    protected AxisParametrs  axisParametrs;
    protected TaskParameters  taskParameters;
    protected DoubleFunction<Double> g;
    protected DoubleFunction<Double> psi;
    protected DoubleFunction<Double> gamma;
    protected DoubleFunction<Double> sigma;
    protected CashIntegralValue cashIntegralValue;
    protected String valueH_in_0;
    protected ControlSpeedBand controlSpeedBand;

    @Override
    public void createCash() {
        double dt = (taskParameters.gettK()-taskParameters.getT0())/MathP.NUMBER_AXIS_PARTITION;
        cashIntegralValue = new CashIntegralValue();
//------------------------------------------------------------------------------------------------------------------
        MathP.integralForCashSorted.apply(taskParameters.getT0(),taskParameters.gettK(),dt,g,cashIntegralValue);
        cashIntegralValue.sortedValue();
    }


    @Override
    public double getControlConstantSpeedBand( double t) {
        return g.apply(t);
    }

    @Override
    public double getSigma( double t) {
        return sigma.apply(t);
    }

    @Override
    public double decision(double s, double t) {
        double C1 = s - cashIntegralValue.getValue(t);
        double tP = cashIntegralValue.getKey(-C1);
        return gamma.apply(tP)/g.apply(tP);
    }

    @Override
    public double decisionCharacteristic(double s, double t) {
        return  cashIntegralValue.getValue(t)  - s;
    }

    @Override
    public AxisParametrs getAxisParametrs() {
        return axisParametrs;
    }
}


class StrategyVConstTimeControlBand01 extends AbstractStrategyVConstTimeControlBand {

    public StrategyVConstTimeControlBand01() {
        axisParametrs = new AxisParametrs(0.0, 10.0, 0.0, 1.0, 0.0, 2.5, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 2.5, 1.0, axisParametrs.getsMin());
        sigma = new Sigma(taskParameters).p2_plus_cosWsigmaT;
        gamma = new Gamma().p1;
        valueH_in_0 = "1.0";          // Determine the form of H(x)-function


        controlSpeedBand = new ControlSpeedBand(taskParameters).setCascadeControlSpeedBand(new double[] {0.8,2.0}).setInitialControlSpeedBand(0.8);

        controlSpeedBand.calculateConstantSpeedBand(sigma, gamma);

        g = new G(taskParameters).setControlConstantSpeedBandList(controlSpeedBand.calculateConstantSpeedBand(sigma, gamma)).controlConstantSpeedBand;

        createCash();
    }
}

class StrategyVConstTimeControlBand02 extends AbstractStrategyVConstTimeControlBand {

    public StrategyVConstTimeControlBand02() {
        axisParametrs = new AxisParametrs(0.0, 10.0, 0.0, 1.0, 0.0, 2.5, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 2.5, 1.0, axisParametrs.getsMin());
        sigma = new Sigma(taskParameters).p2_plus_cosWsigmaT;
        gamma = new Gamma().p1;
        valueH_in_0 = "1.0";          // Determine the form of H(x)-function


        controlSpeedBand = new ControlSpeedBand(taskParameters).setCascadeControlSpeedBand(new double[] {0.5,2.0}).setInitialControlSpeedBand(0.5);

        controlSpeedBand.calculateConstantSpeedBand(sigma, gamma);

        g = new G(taskParameters).setControlConstantSpeedBandList(controlSpeedBand.calculateConstantSpeedBand(sigma, gamma)).controlConstantSpeedBand;

        createCash();
    }
}

class StrategyVConstTimeControlBand03 extends AbstractStrategyVConstTimeControlBand {

    public StrategyVConstTimeControlBand03() {
        axisParametrs = new AxisParametrs(0.0, 10.0, 0.0, 1.0, 0.0, 2.5, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 2.5, 1.0, axisParametrs.getsMin());
        sigma = new Sigma(taskParameters).p2_plus_cosWsigmaT;
        gamma = new Gamma().p1;
        valueH_in_0 = "1.0";          // Determine the form of H(x)-function


        controlSpeedBand = new ControlSpeedBand(taskParameters).setCascadeControlSpeedBand(new double[] {0.8,2.0}).setInitialControlSpeedBand(0.8);

        controlSpeedBand.calculateConstantSpeedBand(sigma, gamma);

        g = new G(taskParameters).setControlConstantSpeedBandList(controlSpeedBand.calculateConstantSpeedBand(sigma, gamma)).controlConstantSpeedBand;

        createCash();
    }
}

class StrategyVConstTimeControlBand04 extends AbstractStrategyVConstTimeControlBand {

    public StrategyVConstTimeControlBand04() {
        axisParametrs = new AxisParametrs(0.0, 10.0, 0.0, 1.0, 0.0, 2.5, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 2.5, 1.0, axisParametrs.getsMin());
        sigma = new Sigma(taskParameters).p2_plus_cosWsigmaT;
        gamma = new Gamma().p1;
        valueH_in_0 = "1.0";          // Determine the form of H(x)-function


        controlSpeedBand = new ControlSpeedBand(taskParameters).setCascadeControlSpeedBand(new double[] {1.0,2.0}).setInitialControlSpeedBand(1.0);

        controlSpeedBand.calculateConstantSpeedBand(sigma, gamma);

        g = new G(taskParameters).setControlConstantSpeedBandList(controlSpeedBand.calculateConstantSpeedBand(sigma, gamma)).controlConstantSpeedBand;

        createCash();
    }
}

class StrategyVConstTimeControlBand05 extends AbstractStrategyVConstTimeControlBand {

    public StrategyVConstTimeControlBand05() {
        axisParametrs = new AxisParametrs(0.0, 10.0, 0.0, 1.0, 0.0, 2.5, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 2.5, 1.0, axisParametrs.getsMin());
        sigma = new Sigma(taskParameters).p2_plus_cosWsigmaT;
        gamma = new Gamma().p1;
        valueH_in_0 = "1.0";          // Determine the form of H(x)-function


        controlSpeedBand = new ControlSpeedBand(taskParameters).setCascadeControlSpeedBand(new double[] {1.2,2.0}).setInitialControlSpeedBand(1.2);

        controlSpeedBand.calculateConstantSpeedBand(sigma, gamma);

        g = new G(taskParameters).setControlConstantSpeedBandList(controlSpeedBand.calculateConstantSpeedBand(sigma, gamma)).controlConstantSpeedBand;

        createCash();
    }
}

class StrategyVConstTimeControlBand06 extends AbstractStrategyVConstTimeControlBand {

    public StrategyVConstTimeControlBand06() {
        axisParametrs = new AxisParametrs(0.0, 10.0, 0.0, 1.0, 0.0, 2.5, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 2.5, 1.0, axisParametrs.getsMin());
        sigma = new Sigma(taskParameters).p2_plus_cosWsigmaT;
        gamma = new Gamma().p1;
        valueH_in_0 = "1.0";          // Determine the form of H(x)-function


        controlSpeedBand = new ControlSpeedBand(taskParameters).setCascadeControlSpeedBand(new double[] {1.5,2.0}).setInitialControlSpeedBand(1.5);

        controlSpeedBand.calculateConstantSpeedBand(sigma, gamma);

        g = new G(taskParameters).setControlConstantSpeedBandList(controlSpeedBand.calculateConstantSpeedBand(sigma, gamma)).controlConstantSpeedBand;

        createCash();
    }
}

class StrategyVConstTimeControlBand07 extends AbstractStrategyVConstTimeControlBand {

    public StrategyVConstTimeControlBand07() {
        axisParametrs = new AxisParametrs(0.0, 10.0, 0.0, 1.0, 0.0, 2.5, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 2.5, 1.0, axisParametrs.getsMin());
        sigma = new Sigma(taskParameters).p2_plus_cosWsigmaT;
        gamma = new Gamma().p1;
        valueH_in_0 = "1.0";          // Determine the form of H(x)-function


        controlSpeedBand = new ControlSpeedBand(taskParameters).setCascadeControlSpeedBand(new double[] {1.8,2.0}).setInitialControlSpeedBand(1.8);

        controlSpeedBand.calculateConstantSpeedBand(sigma, gamma);

        g = new G(taskParameters).setControlConstantSpeedBandList(controlSpeedBand.calculateConstantSpeedBand(sigma, gamma)).controlConstantSpeedBand;

        createCash();
    }
}

//class StrategyVConstTimeControlBand02 extends AbstractStrategyVConstTimeControlBand {
//
//    public StrategyVConstTimeControlBand02() {
//        axisParametrs = new AxisParametrs(0.0, 10.0, 0.0, 1.0, 0.0, 2.0, 10.0, 10.0);
//        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 2.5, 1.0, axisParametrs.getsMin());
//        sigma = new Sigma(taskParameters).p2_plus_cosWsigmaT;
//        gamma = new Gamma().p1;
//        valueH_in_0 = "1.0";          // Determine the form of H(x)-function
//
//
//        controlSpeedBand = new ControlSpeedBand(taskParameters).setCascadeControlSpeedBand(new double[] {0.5,2.0}).setInitialControlSpeedBand(0.5);
//
//        controlSpeedBand.calculateConstantSpeedBand(sigma, gamma);
//
//        g = new G(taskParameters).setControlConstantSpeedBandList(controlSpeedBand.calculateConstantSpeedBand(sigma, gamma)).controlConstantSpeedBand;
//
//        createCash();
//    }
//}