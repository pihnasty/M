package trestview.machinetest.animation;

import trestview.machinetest.module0.Module0Model;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Роман on 18.04.2016.
 */
public class ProcessingAnimationModel extends Observable implements Observer {

    //gets tau and transforms it into duration for animation

    private double duration; //h

    public ProcessingAnimationModel() {

    }

    private void findDuration(double rndVal) {
        System.out.println("In Animation Model");
        duration = rndVal*10;
    }

    public double getDuration() {
        return duration;
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o.getClass() == Module0Model.class) {
            findDuration(((Module0Model)o).getRandomVariable());
            changed();
        }
    }
}
