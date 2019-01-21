package trestview.machinetest.module4;

import trestview.machinetest.module0.Module0Model;

import java.util.*;

/**
 * Created by Роман on 15.04.2016.
 */
public class Module4Model extends Observable implements Observer{

    Module0Model module0Model;

    private ArrayList<Double> randomValuesList;//tau
    private ArrayList<Double> expectedValues;
    private ArrayList<Long> times;
    private ArrayList<Double> temps;

    private double constantVal;


    private ArrayList<Double> groupedStatisticalSeries;
//    private Timer timer;

    public Module4Model(Module0Model module0Model) {
        this.module0Model = module0Model;
        this.randomValuesList = new ArrayList<>();
        this.expectedValues = new ArrayList<>();
        this.times = new ArrayList<>();
        this.temps = new ArrayList<>();
    }

    public ArrayList<Double> getExpectedValues() {
        return expectedValues;
    }

    public ArrayList<Long> getTimes() {
        return times;
    }

    public ArrayList<Double> getTemps() {
        return temps;
    }

    public double getConstantVal() {
        constantVal = 0.05;
        return constantVal;
    }

    private void runExpectedValue(int amount) {
        double sum = 0;
//        double k = amount;

//        if(amount>randomValuesList.size()) //amount = randomValuesList.size();
        for(int i = randomValuesList.size()-amount; i < randomValuesList.size(); i++) {
            sum += randomValuesList.get(i);
        }
        double expectedVal = sum/amount;
        expectedValues.add(expectedVal);
        System.out.println(expectedValues+"jjjjjjj");
    }

    private void runTemp() {
        double temp = 0;
        double average = expectedValues.get(expectedValues.size()-1);
        temp = 1/average;
        temps.add(temp);
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
                runExpectedValue(25);
                runTemp();
            }
                mesureTime();

//            normalize();
//            createExpectedValues();
            changed();
        }

    }
}
