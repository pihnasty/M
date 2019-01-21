package trestview.machinetest.module3;

import javafx.collections.ObservableList;
import trestview.machinetest.module0.Module0Model;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Роман on 15.04.2016.
 */
public class Module3Model extends Observable implements Observer{

    Module0Model module0Model;

    private int minValue;
    private int maxValue;
    private double step;


    private ArrayList<Double> randomValuesList;//tau
    private ArrayList<Double> groupedStatisticalSeries;//HashSet
    private ArrayList<Double> normStatisticalSeries;//HashSet
    private ArrayList<Integer> ranks;
//    private Timer timer;

    public Module3Model(Module0Model module0Model) {
        this.module0Model = module0Model;
        this.randomValuesList = new ArrayList<>();
        this.groupedStatisticalSeries = new ArrayList<>();
        this.normStatisticalSeries = new ArrayList<>();
        this.ranks = new ArrayList<>();
    }

    public ArrayList<Double> getRandomValuesList() {
        return randomValuesList;
    }

    public ArrayList<Double> getGroupedStatisticalSeries() {
        return groupedStatisticalSeries;
    }

    public ArrayList<Double> getNormStatisticalSeries() {
        return normStatisticalSeries;
    }

    public ArrayList<Integer> getRanks() {
        return ranks;
    }

    public void setRandomValuesList(ArrayList<Double> randomValuesList) {
        this.randomValuesList = randomValuesList;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getStep() {
        return step;
    }



    //populate the List with a test data
    private void createGroupedStatisticalSeries(int stepsCount) {

        resizeGroupedStatisticalSeries(stepsCount);
//        stepsCount = 10;
        //1.sort
        sort(randomValuesList);
        //2.min max
        findMaxAndMin(randomValuesList);
        //3.find limits
        setLimits();
        //
        findFrequencies(stepsCount);

    }

    private void resizeGroupedStatisticalSeries(int stepsCount) {
        groupedStatisticalSeries.clear();
//        if(groupedStatisticalSeries.size() != stepsCount) {
//            for(int i = 0; i < stepsCount; i++) {
//                groupedStatisticalSeries.add(0.0);
//            }
//        }
    }

    private void sort(List<Double> list) {
        Collections.sort(list);
//        System.out.println(list);
    }

    private void findMaxAndMin(List<Double> list) {
        if(list.size() > 0) {
            maxValue = list.get(list.size() - 1).intValue();//last element
            minValue = list.get(0).intValue();//first element
        } else {
            maxValue = 50;
            minValue = 0;
        }

    }

    private void setLimits() {
        //temporary solution
        minValue = 0;
        maxValue = 100;
    }

    private void normalize() {
        normStatisticalSeries.clear();
        double normedVal;
        double size = randomValuesList.size();
//        double s = 0;
        for(double val : groupedStatisticalSeries) {
            normedVal = val/(size*step);
            normStatisticalSeries.add(normedVal);
//            s+=normedVal;
        }
        normStatisticalSeries.add(0.0);

        System.out.println("-----"+normStatisticalSeries);
    }

    private void findFrequencies(double stepsCount) {

        step = (maxValue - minValue)/stepsCount;
        ranks.clear();
        for( int r = minValue; r <= maxValue; r+=step) {
            ranks.add(r);
        }
        ranks.add((int)(ranks.get(ranks.size()-1)+step));

        for(int rank : ranks) {
            double count = 0;
            for(double tau : randomValuesList) {
                if(tau < rank && tau > rank-step) {
                    count++;
                }
            }
            groupedStatisticalSeries.add(count);
        }
//        System.out.println("---ranks:"+ranks);
//        System.out.println("---frequencies:"+groupedStatisticalSeries);

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
            createGroupedStatisticalSeries(10);
            normalize();
            changed();
        }

    }
}
