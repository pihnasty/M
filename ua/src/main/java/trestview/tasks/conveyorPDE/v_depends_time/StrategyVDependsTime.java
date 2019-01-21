package trestview.tasks.conveyorPDE.v_depends_time;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.stream.Collectors;

public interface StrategyVDependsTime {

    double decision(double s, double t);

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
    * */
    class TaskParameters {

        protected double s0;
        protected double t0 = 0.0;
        protected double tK = new AxisParametrs().tMax;
        protected double tS;

        public TaskParameters() {
        }

        public TaskParameters(double t0, double tK, double tS) {
            this(t0, tK, tS, 0.0);
        }

        public TaskParameters(double t0, double tK, double tS, double s0) {
            this.tS = tS;
            this.t0 = t0;
            this.tK = tK;
            this.s0 = s0;
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

    class G {
        private TaskParameters taskParameters;
        protected DoubleFunction<Double> g0_g1coswt;
        private double wS;

        public G() {
            this(new TaskParameters());
        }

        public G(TaskParameters taskParameters) {
            this.taskParameters = taskParameters;
            this.wS = 2.0 * Math.PI / taskParameters.tS;
            g0_g1coswt = t -> 2.0 - Math.cos(wS * t);
            // There are .....
        }
    }

    class Gamma {
        protected DoubleFunction<Double> h = t -> MathP.h(t);
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

        static final double NUMBER_AXIS_PARTITION = 100000;

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

class AbstractStrategyVDependsTime implements StrategyVDependsTime {
    protected AxisParametrs  axisParametrs;
    protected TaskParameters  taskParameters;
    protected DoubleFunction<Double> g;
    protected DoubleFunction<Double> psi;
    protected DoubleFunction<Double> gamma;
    protected CashIntegralValue cashIntegralValue;
    protected String valueH_in_0;

    @Override
    public void createCash() {
        double dt = (taskParameters.gettK()-taskParameters.getT0())/MathP.NUMBER_AXIS_PARTITION;
        cashIntegralValue = new CashIntegralValue();
//------------------------------------------------------------------------------------------------------------------
        MathP.integralForCashSorted.apply(taskParameters.getT0(),taskParameters.gettK(),dt,g,cashIntegralValue);
        cashIntegralValue.sortedValue();
    }

    @Override
    public double decision(double s, double t) {
        double C1 = s - cashIntegralValue.getValue(t);
        double tP = cashIntegralValue.getKey(-C1);
        return MathP.h(s,valueH_in_0)*gamma.apply(tP)/g.apply(tP) - MathP.h(C1,valueH_in_0)*gamma.apply(tP)/g.apply(tP)
                +MathP.h(C1,valueH_in_0)*psi.apply(C1);
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


class StrategyVDependsTime01 extends AbstractStrategyVDependsTime {

    public StrategyVDependsTime01() {
        axisParametrs = new AxisParametrs(0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 0.5, axisParametrs.getsMin());
        g = new G(taskParameters).g0_g1coswt;
        psi = new Psi().hm1_S;
        gamma = new Gamma().h;
        valueH_in_0 = "1.0";
        createCash();
    }
}

class StrategyVDependsTime02 extends AbstractStrategyVDependsTime {

    public StrategyVDependsTime02() {
        axisParametrs = new AxisParametrs(0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 10.0, 10.0);
        taskParameters = new TaskParameters(axisParametrs.gettMin(), axisParametrs.gettMax(), 0.25, axisParametrs.getsMin());
        g = new G(taskParameters).g0_g1coswt;
        psi = new Psi().hm_1_plus_sinS;
        gamma = new Gamma().h;
        valueH_in_0 = "0.5";
        createCash();
    }
}