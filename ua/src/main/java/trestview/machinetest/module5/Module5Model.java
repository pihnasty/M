package trestview.machinetest.module5;


import trestview.machinetest.module0.Module0Model;

import java.util.*;

/**
 * Created by Роман on 15.04.2016.
 */
public class Module5Model extends Observable implements Observer{

    Module0Model module0Model;
    private ArrayList<Double> randomValuesList;//tau
    private ArrayList<Double> standardDeviations;
    private ArrayList<Double> listSigma;
    private ArrayList<Long> times;
//    private Timer timer;

    public Module5Model(Module0Model module0Model) {
        this.module0Model = module0Model;
        this.randomValuesList = new ArrayList<>();
        this.listSigma = new ArrayList<>();
        this.standardDeviations = new ArrayList<>();
        this.times = new ArrayList<>();
    }

    public ArrayList<Double> getRandomValuesList() {
        return randomValuesList;
    }

    public ArrayList<Long> getTimes() {
        return times;
    }

    public Module0Model getModule0Model() {
        return module0Model;
    }

    public ArrayList<Double> getStandardDeviations() {
        return standardDeviations;
    }

    public ArrayList<Double> getListSigma() {
        return listSigma;
    }

    private double findAverage() {
        double sum = 0;
        for (double tau : randomValuesList) {
            sum += tau;
        }
        return sum/randomValuesList.size();
    }

    private void runSigma(int amount) {

        if(amount>randomValuesList.size())
            amount = randomValuesList.size();
        for(int i = randomValuesList.size()-amount; i < randomValuesList.size(); i++) {
            double sigma = Math.abs(randomValuesList.get(i) - findAverage());
            listSigma.add(sigma);
        }
        System.out.println("sigmaaa"+listSigma);
    }

    private void runStandartDeviation(int amount) {
        runSigma(amount);
        double sum = 0;

        if(amount>listSigma.size()) amount = listSigma.size();
        for(int i = listSigma.size()-amount; i < listSigma.size(); i++) {
            sum += listSigma.get(i);
        }
        double res = sum/amount;
        standardDeviations.add(res);
//        System.out.println(standardDeviations+"jjjjjjj");
    }

    private void mesureTime() {
        long time = 0;
        time+=3;
        times.add(time);
    }

    public void changed() {
        setChanged();
        notifyObservers();

    }

    @Override
    public void update(Observable o, Object arg) {

        if(o.getClass() == (Module0Model.class)) {
            double tau = ((Module0Model) o).getRandomVariable();
            randomValuesList.add(tau);
            if(randomValuesList.size() >= 25) {
                runStandartDeviation(25);
            }
            changed();
        }

    }
}
