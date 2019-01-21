package trestview.machinetest.animation;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import trestview.machinetest.MachineTestController;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

/**
 * Created by Роман on 18.04.2016.
 */
public class ProcessingAnimationView extends Pane implements Observer {


    private ProcessingAnimationModel processingAnimationModel;
    private MachineTestController machineTestController;

    private ImageView imageViewTop;
    private ImageView imageViewBot;
    private ImageView imageViewLeftStorage;
    private ImageView imageViewRightStorage;
    private Image imgTop;
    private Image imgBot;
    Rectangle rectangle;
    Circle circle;
    Timeline timeline;
    TranslateTransition transitionCircle;
    TranslateTransition transitionRect;

    private Stack<Rectangle> products;
    private Stack<Circle> circleStack;

    public ProcessingAnimationView(ProcessingAnimationModel animationModel) {//ProcessingAnimationModel animationModel, MachineTestController testController) {
        this.processingAnimationModel = animationModel;
//        this.machineTestController = testController;

        imageViewTop = new ImageView();
        imageViewTop.setFitWidth(270);
        imageViewTop.setFitHeight(100);
        imageViewTop.setX(200);
        imgTop = new Image("file:Image\\animation\\presTop.png");
        imageViewTop.setImage(imgTop);

        imageViewBot = new ImageView();
        imageViewBot.setTranslateY(40);
        imageViewBot.setFitWidth(270);
        imageViewBot.setFitHeight(300);
        imgBot = new Image("file:Image\\animation\\presBot.png");
        imageViewBot.setPreserveRatio(true);
        imageViewBot.setX(200);
        imageViewBot.setImage(imgBot);

        Image imgStorage = new Image("file:Image\\animation\\storage.png");
        imageViewLeftStorage = new ImageView();
        imageViewLeftStorage.setFitWidth(190);
        imageViewLeftStorage.setFitHeight(120);
        imageViewLeftStorage.setY(180);
        imageViewLeftStorage.setImage(imgStorage);

        imageViewRightStorage = new ImageView();
        imageViewRightStorage.setFitWidth(190);
        imageViewRightStorage.setFitHeight(120);
        imageViewRightStorage.setY(180);
        imageViewRightStorage.setX(500);
        imageViewRightStorage.setImage(imgStorage);


        rectangle = new Rectangle(340, 185, 15, 5);
//        rectangle.setVisible(false);
        rectangle.setFill(Color.CORAL);
        circle = new Circle(190, 180, 10);
        circle.setFill(Color.CORAL);

        this.getChildren().addAll(imageViewLeftStorage, imageViewRightStorage, rectangle, circle, imageViewBot, imageViewTop);

        this.setMinSize(300, 300);

        timeline = new Timeline();
        transitionCircle = new TranslateTransition();
        transitionRect = new TranslateTransition();
        products = new Stack<>();
        circleStack = new Stack<>();
        fillLeftStorage(this, imageViewLeftStorage);
    }

    private void runProcessingAnimation(double duration) {

        System.out.println("In Processing ANimation");
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2);

//        transitionRect = new TranslateTransition(Duration.millis(duration), rectangle);
//        transitionCircle = new TranslateTransition(Duration.millis(duration), circle);
        transitionCircle.setDuration(Duration.millis(duration));
        transitionCircle.setNode(circle);
        transitionCircle.setAutoReverse(false);
        transitionCircle.setByX(150);
        transitionCircle.setFromX(0);

        transitionRect.setAutoReverse(false);
        transitionRect.setDuration(Duration.millis(duration));
        transitionRect.setNode(rectangle);
        transitionRect.setByX(140);
        transitionRect.setFromX(0);

        KeyValue keyValue = new KeyValue(imageViewTop.translateYProperty(), 90);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), (ActionEvent)->{
            toggleHide(rectangle);
                transitionRect.play();
                transitionCircle.play();
                stackProducts(rectangle, imageViewRightStorage, this);
                //toggleHide(rectangle);

        },keyValue);

        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void toggleHide(Shape... shapes) {
        for(Shape shape : shapes) {
            if(shape.isVisible()) {
                shape.setVisible(false);
            } else {
                shape.setVisible(true);
            }
        }
    }

    private void stackProducts(Rectangle rectangle, ImageView box, Pane layout) {

        Rectangle rect = null;

        rect = new Rectangle();
        rect.setWidth(rectangle.getWidth());
        rect.setHeight(rectangle.getHeight());
        rect.setFill(rectangle.getFill());
        //}
        if(products.isEmpty()) {
            rect.setX(box.getX()+box.getFitWidth()-25);
            rect.setY(box.getY()-rect.getHeight()+box.getFitHeight()-30);

        } else if(products.peek().getX() < box.getX()+20){
            rect.setY(products.peek().getY() - rect.getHeight() -2);
            rect.setX(box.getX()+box.getFitWidth()-25);

        } else {
            rect.setX(products.peek().getX()-rect.getWidth() - 2);
            rect.setY(products.peek().getY());
        }
        products.add(rect);
        layout.getChildren().add(rect);

    }

    private void fillLeftStorage(Pane layout, ImageView box) {
        Circle circle;
        for(int i = 0; i < 25; i++) {
            circle = new Circle(10, Color.CORAL);
            if(circleStack.isEmpty()) {
                circle.setCenterX(box.getX()+box.getFitWidth()-20);
                circle.setCenterY(box.getY()-circle.getRadius()+box.getFitHeight()-30);

            } else if(circleStack.peek().getCenterX() < box.getX()+30){
                circle.setCenterY(circleStack.peek().getCenterY() - 2*circle.getRadius() -2);
                circle.setCenterX(box.getX()+box.getFitWidth()-20);

            } else {
                circle.setCenterX(circleStack.peek().getCenterX()-2*circle.getRadius() - 1);
                circle.setCenterY(circleStack.peek().getCenterY());
            }
            circleStack.add(circle);
            layout.getChildren().add(circle);
        }
    }

    private void removeItemFromStorage() {
        this.getChildren().remove(circleStack.pop());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o.getClass() == ProcessingAnimationModel.class) {
//            circleStack.pop();
            if(circleStack.empty()) {
                fillLeftStorage(this, imageViewLeftStorage);
            }
            removeItemFromStorage();
            //toggleHide(rectangle);
            Platform.runLater(()->runProcessingAnimation(((ProcessingAnimationModel)o).getDuration()));
            //runProcessingAnimation(((ProcessingAnimationModel)o).getDuration());

        }
    }
}
