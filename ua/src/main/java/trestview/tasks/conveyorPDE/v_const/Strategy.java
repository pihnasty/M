package trestview.tasks.conveyorPDE.v_const;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public interface Strategy {
    double _s0[] = {0.0, 0.2};     double _t0[] = {0.0, 0.2};    double numberOfCurves[] = {10.0};    double g[] = {1.0,1.0};
    double tMax[] = {1.0};         double tMin[] = {0.0};
    double yMax[] = {1.0};         double yMin[] = {0.0};         double yTickUnitNumber[] = {10.0};

    double sMax[] = {1.0};
    double _sD[] = {1.0, 0.2};                             // It is _sD[k]=sDk/sDo
    double g0[] = {g[0]* _sD[0],g[1]* _sD[1]};             // It is g0[k]=g[k]*sDo/sDk

    DoubleFunction<Double> sinConveyorPdeModelS = _r -> (2 + Math.sin(2 * Math.PI * _r)) / 3.0;
    DoubleFunction<Double> sinConveyorPdeModelT = _r -> (2 + Math.sin(2 * Math.PI * (0.5 - _r))) / 3.0;
    DoubleFunction<Double> const_1 = _r -> 1.0;
    DoubleFunction<Double> const_x = _r -> _r;

    double decision(double _s, double _t);

    double decisionCharacteristic(double _s, double _t);

    List<DoubleFunction<Double>> getInitialConditions();

    default  List<DoubleFunction<Double>> getBoundaryConditions() {
        return null;
    }

    default double r(double _s, double _t, double _s0, double _t0, double g) {
        return _s - _s0 - g * (_t - _t0);
    }

    default double h(double r) {
        if (r < 0) return 0;
        if (r == 0) return 0.5;
        else return 1.0;
    }

    default double get_s0() {
        return _s0[0];
    }

    default double get_t0() {
        return _t0[0];
    }

    default double getNumberOfCurves() {
        return numberOfCurves[0];
    }

    default double getG0() {
        return g[0];
    }

    default double getTmax() {
        return tMax[0];
    }

    default double getTMin() {
        return tMin[0];
    }

    default double getSmax() {
        return sMax[0];
    }

    default double getYmax() {
        return yMax[0];
    }

    default double getYMin() {
        return yMin[0];
    }

    default double getYtickUnit() {
        return (yMax[0]-yMin[0])/yTickUnitNumber[0];
    }
}


class Strategy01 implements Strategy {
    public Strategy01() {
        _s0[0] = 0.0;        _t0[0] = 0.0;        numberOfCurves[0] = 10.0;        g[0] = 1.0;
        tMax[0] = 1.0;       tMin[0] = 0.0;       sMax[0] = 1.0;
        yMax[0] = 1.0;       yMin[0] = 0.0;       yTickUnitNumber[0] = 10.0;
    }

    @Override
    public double decision(double _s, double _t) {
        double _r = r(_s, _t, get_s0(), get_t0(), getG0());
        return getInitialConditions().get(0).apply(_r + get_s0());
    }

    @Override
    public double decisionCharacteristic(double _s, double _t) {
        return  r(get_s0(), get_t0(), _s, _t, getG0());
    }

    @Override
    public List<DoubleFunction<Double>> getInitialConditions() {
        List<DoubleFunction<Double>> initialConditions = new ArrayList<>();
        initialConditions.add(sinConveyorPdeModelS);
        return  initialConditions ;
    }
}

class Strategy02 implements Strategy {
    public Strategy02() {
        _s0[0] = 0.0;        _t0[0] = 0.0;        numberOfCurves[0] = 10.0;        g[0] = 2.0;
        tMax[0] = 1.0;       tMin[0] = 0.0;       sMax[0] = 1.0;
        yMax[0] = 1.0;       yMin[0] = 0.0;       yTickUnitNumber[0] = 10.0;
    }

    @Override
    public double decision(double _s, double _t) {
        double _r = r(_s, _t, get_s0(), get_t0(), getG0());
        return getInitialConditions().get(0).apply(_r + get_s0());
    }

    @Override
    public double decisionCharacteristic(double _s, double _t) {
        return  r(get_s0(), get_t0(), _s, _t, getG0());
    }

    @Override
    public List<DoubleFunction<Double>> getInitialConditions() {
        List<DoubleFunction<Double>> initialConditions = new ArrayList<>();
        initialConditions.add(sinConveyorPdeModelS);
        return  initialConditions ;
    }
}

class Strategy03 implements Strategy {
    public Strategy03() {
        _s0[0] = 0.2;        _t0[0] = 0.0;        numberOfCurves[0] = 10.0;        g[0] = 1.0;
        _s0[1] = 0.0;        _t0[1] = 0.0;                                         g[1] = 1.0;

        _sD[1] = 0.2;

        g0[1] = g[1]* _sD[1];

        tMax[0] = 1.0;       tMin[0] = 0.0;       sMax[0] = 1.0;
        yMax[0] = 2.0;       yMin[0] = 0.0;       yTickUnitNumber[0] = 10.0;
    }

    @Override
    public double decision(double _s, double _t) {
        double _r[] = {r(_s, _t, _s0[0], _t0[0],  g[0])
                      ,r(1.0, _t, _s0[1], _t0[1], g0[1])
        };


        return getInitialConditions().get(0).apply(_r[0] + _s0[0])
                +h(_s-_s0[0])

                *getBoundaryConditions().get(1).apply(_t0[1]-_r[1]/g0[1]);

    }

    @Override
    public double decisionCharacteristic(double _s, double _t) {
        return  r(get_s0(), get_t0(), _s, _t, getG0());
    }

    @Override
    public List<DoubleFunction<Double>> getInitialConditions() {
        List<DoubleFunction<Double>> initialConditions = new ArrayList<>();
        initialConditions.add(sinConveyorPdeModelS);
        initialConditions.add(null);
        return  initialConditions ;
    }

    @Override
    public List<DoubleFunction<Double>> getBoundaryConditions() {
        List<DoubleFunction<Double>> boundaryConditions = new ArrayList<>();
        boundaryConditions.add(null);
        boundaryConditions.add(const_1);
        return  boundaryConditions ;
    }

}

class Strategy04 implements Strategy {
    public Strategy04() {
        _s0[0] = 0.2;        _t0[0] = 0.0;        numberOfCurves[0] = 10.0;        g[0] = 1.0;
        _s0[1] = 0.0;        _t0[1] = 0.0;                                         g[1] = 1.0;

        _sD[1] = 5.0;

        g0[1] = g[1]* _sD[1];

        tMax[0] = 1.0;       tMin[0] = 0.0;       sMax[0] = 1.0;
        yMax[0] = 2.0;       yMin[0] = 0.0;       yTickUnitNumber[0] = 10.0;
    }

    @Override
    public double decision(double _s, double _t) {
        double _r[] = {r(_s, _t, _s0[0], _t0[0],  g[0])
                ,r(1.0, _t, _s0[1], _t0[1], g0[1])
        };


        return getInitialConditions().get(0).apply(_r[0] + _s0[0])
                +h(_s-_s0[0])*
                functionQ1(1.0,_t0[0]-_r[0]/g[0]);

//                *getBoundaryConditions().get(1).apply(_t0[1]-_r[1]/g0[1]);

    }

    double functionQ1 (double _s, double _t) {
        return  h(-r(_s, _t, _s0[1], _t0[1], g0[1])/g0[1])*
                // h(_t-r(_S, _t, _s0[1], _t0[1], g0[1])/g0[1])*
                getBoundaryConditions().get(1).apply(-r(_s, _t, _s0[1], _t0[1], g0[1])/g0[1])
                -h(_s-_s0[1])*h(-_s+_s0[1])
                ;
    }

    @Override
    public double decisionCharacteristic(double _s, double _t) {
        return  r(get_s0(), get_t0(), _s, _t, getG0());
    }

    @Override
    public List<DoubleFunction<Double>> getInitialConditions() {
        List<DoubleFunction<Double>> initialConditions = new ArrayList<>();
        initialConditions.add(sinConveyorPdeModelS);
        initialConditions.add(null);
        return  initialConditions ;
    }

    @Override
    public List<DoubleFunction<Double>> getBoundaryConditions() {
        List<DoubleFunction<Double>> boundaryConditions = new ArrayList<>();
        boundaryConditions.add(null);
        boundaryConditions.add(const_1);
        return  boundaryConditions ;
    }

}

class Strategy05 implements Strategy {
    public Strategy05() {
        _s0[0] = 0.2;        _t0[0] = 0.0;        numberOfCurves[0] = 10.0;        g[0] = 1.0;
        _s0[1] = 0.0;        _t0[1] = 0.0;                                         g[1] = 1.0;

        _sD[1] = 5.0;

        g0[1] = g[1]* _sD[1];

        tMax[0] = 1.0;       tMin[0] = 0.0;       sMax[0] = 1.0;
        yMax[0] = 2.0;       yMin[0] = 0.0;       yTickUnitNumber[0] = 10.0;
    }

    @Override
    public double decision(double _s, double _t) {
        double _r[] = {r(_s, _t, _s0[0], _t0[0],  g[0])
                ,r(1.0, _t, _s0[1], _t0[1], g0[1])
        };


        return getInitialConditions().get(0).apply(_r[0] + _s0[0])
                +h(_s-_s0[0])*
                functionQ1(1.0,_t0[0]-_r[0]/g[0]);

//                *getBoundaryConditions().get(1).apply(_t0[1]-_r[1]/g0[1]);

    }

    double functionQ1 (double _S, double _t) {
        return  h(_t-r(_S, _t, _s0[1], _t0[1], g0[1])/g0[1])*
                getBoundaryConditions().get(1).apply(_t0[1]-r(_S, _t, _s0[1], _t0[1], g0[1])/g0[1]);
    }

    @Override
    public double decisionCharacteristic(double _s, double _t) {
        return  r(get_s0(), get_t0(), _s, _t, getG0());
    }

    @Override
    public List<DoubleFunction<Double>> getInitialConditions() {
        List<DoubleFunction<Double>> initialConditions = new ArrayList<>();
        initialConditions.add(sinConveyorPdeModelS);
        initialConditions.add(null);
        return  initialConditions ;
    }

    @Override
    public List<DoubleFunction<Double>> getBoundaryConditions() {
        List<DoubleFunction<Double>> boundaryConditions = new ArrayList<>();
        boundaryConditions.add(null);
        boundaryConditions.add(const_x);
        return  boundaryConditions ;
    }

}