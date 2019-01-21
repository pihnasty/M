package trestview.machinetest.module0;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.*;

/**
 * Created by Роман on 03.05.2016.
 */
public class Module0Model extends Observable {

//    private Deque<Double> randomVariablesList;

    private double randomVariable;

    private Timer timer;

    public Module0Model() {
//        this.randomVariablesList = new ArrayDeque<>();
//        this.groupedStatisticalSeries = new Hashtable<>();
       // populateList(100);
    }

    public double getRandomVariable() {
        return randomVariable;
    }



//    public void setRandomValuesList(Deque<Double> randomVariablesList) {
//        this.randomVariablesList = randomVariablesList;
//
//    }
    /**
     *
     * populate the List with a test data
     */
    public void populateList(int limit) {

//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        System.out.println("Timer is working");
//                        double value = Math.random() * 100;
////                        System.sout.println(value+"\\(^-^)/");
//                        randomVariable = value;
//
//                        changed();
//                    }
//                });
//            }
//        }, 3000, 3000);//3000000, 300000000);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //this is called every 3 seconds on UI thread
                double value = Math.random() * 100;
                randomVariable = value;
                changed();
                //System.out.println(value);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

}
