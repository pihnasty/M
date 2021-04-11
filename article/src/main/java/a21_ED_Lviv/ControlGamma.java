package a21_ED_Lviv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControlGamma extends Thread {

    private static int maneNumber = 1;

    private final double deltaTau1;
    private final double tauLoad;
    private final double deltaTau;
    private final double flowRestriction;
    private final double controlGammaValueMax;
    private double tau = 0.0;
    double controlGammaValue = 0.0;
    private final ControlGamma controlGammaInput;
    List<Double> taus;

    List<Double> controlGammaValues;

    public ControlGamma(
        double deltaTau
        , double tauLoad
        , double deltaTau1
        , double controlGammaValueMax
        , double flowRestriction
        , ControlGamma controlGammaInput) {
        this.deltaTau = deltaTau;
        this.tauLoad = tauLoad;
        this.deltaTau1 = deltaTau1;
        this.controlGammaValueMax = controlGammaValueMax;
        this.flowRestriction = flowRestriction;
        this.controlGammaInput = controlGammaInput;
        taus = new ArrayList<>();
        controlGammaValues = new ArrayList<>();
        this.setName("ControlGamma" + maneNumber++);
        start();
    }

    private synchronized void execute () {
        controlGammaValue = 0.0;
        if(isNeedOn()) {
            controlGammaValue = flowRestriction - getSumControlGammaInput(tau, 0.0, this);
        }
        if (controlGammaValue > controlGammaValueMax) {
            controlGammaValue = controlGammaValueMax;
        }
        controlGammaValues.add(controlGammaValue);
        taus.add(tau);
        notify();
    }

    public void run () {
        for (; tau<tauLoad; tau+=deltaTau) {
            execute();
        }
    }

    private synchronized boolean isNeedOn () {
        boolean isRule1 = isRule1(tau, tauLoad - deltaTau1);
        double summaryFlow = 0.0;
        summaryFlow = getSumControlGammaInput(tau, summaryFlow,  this);
        boolean isRule3 = isRule1(summaryFlow, flowRestriction);
        return isRule1 && isRule3;
    }

    private synchronized boolean isRule1(double tau, double v) {
        return tau <= v;
    }

    private synchronized double getSumControlGammaInput(double tau, double summaryFlow, ControlGamma controlGamma) {
        ControlGamma controlGammaInput = controlGamma.controlGammaInput;
        if(Objects.isNull(controlGammaInput)) {
            return summaryFlow;
        }
        tau =  tau+ controlGamma.deltaTau1- controlGammaInput.deltaTau1;
        summaryFlow += controlGammaInput.getControlGamma(tau);
//        try {
//            summaryFlow += controlGammaInput.getControlGamma(tau);
//        } catch (Exception e) {
//            System.out.println();
//        }


        return getSumControlGammaInput(tau, summaryFlow, controlGamma.controlGammaInput);
    }

    public synchronized List<Double> getControlGammas() {
        return controlGammaValues;
    }

    public List<Double> getTaus() {
        return taus;
    }

    public synchronized double getControlGamma(double tau) {

        while (controlGammaValues.size()==0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int number = (int) (tau / deltaTau);
        if(number<0) {
            number =0;
        }

        if(number>controlGammaValues.size()-1) {
            number =controlGammaValues.size()-1;
        }


        return controlGammaValues.get(number);
    }

}
