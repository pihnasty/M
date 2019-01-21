package trestview.orderplaninigperspective.tables;

import entityProduction.Line;
import entityProduction.Order;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Username: dummy_user
 * Date: 5/14/2017
 * Time: 9:05 PM
 */
public class MyView extends StackPane implements Observer {

    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.BURLYWOOD, Color.TOMATO, Color.CYAN, Color.BROWN};

    private MyModel myModel;
    private MyController myController;


    public MyView(MyModel myModel, MyController myController) {
        this.myModel = myModel;
        this.myController = myController;

        initView();
    }

    private void initView() {
        this.getChildren().clear();

        this.setWidth(1800);
        this.setHeight(500);

        VBox boxWithLines = new VBox();
        VBox boxWithGraphics = new VBox();
        HBox hBox = new HBox();


        for (Order order : myModel.getWork().getOrders()) {
            long orderDuration = order.getDateEnd().getTime() - order.getDateBegin().getTime();

            VBox boxWithLinesInner = new VBox();
            VBox boxWithGraphicsInner = new VBox();
            boxWithLinesInner.getChildren().add(new Label(order.getName()));

            for (Line line : order.getLines()) {
                int colorIndex = new Random().nextInt(colors.length);


                Label labelLine = new Label(line.getName());
                labelLine.setTextFill(colors[colorIndex]);

                boxWithLinesInner.getChildren().add(labelLine);


                long lineDuration = line.getDateEnd().getTime() - line.getDateBegin().getTime();
                Rectangle lineDurationGraphic = new Rectangle(TimeUnit.MILLISECONDS.toDays(lineDuration), 17);
                lineDurationGraphic.setFill(colors[colorIndex]);


                boxWithGraphicsInner.getChildren().add(lineDurationGraphic);
            }
            long orderDurationByDays = TimeUnit.MILLISECONDS.toDays(orderDuration);

            StackPane paneWithGraphics = new StackPane();
            paneWithGraphics.getChildren().add(boxWithGraphicsInner);
            paneWithGraphics.setPadding(new Insets(0, 0, 0, orderDurationByDays));


            boxWithLinesInner.setPadding(new Insets(20));

            //TODO set it into stylesheets
            boxWithGraphicsInner.setStyle(
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-color: blue;");
            boxWithGraphicsInner.setPadding(new Insets(30, 0, 30, 0));


            boxWithGraphics.getChildren().add(paneWithGraphics);
            boxWithLines.getChildren().add(boxWithLinesInner);

        }

        boxWithGraphics.getChildren().add(writeAxesOx());

        hBox.getChildren().addAll(boxWithLines, boxWithGraphics);

        this.getChildren().add(hBox);
    }

    private StackPane writeAxesOx() {
        StackPane stackPane = new StackPane();


        javafx.scene.shape.Line line = new javafx.scene.shape.Line(0, 0, 400, 0);
        line.setFill(Color.BLACK);

        stackPane.getChildren().add(line);

        for (int i = 0; i < 400; i += 40) {
            Label axes = new Label(String.valueOf(i));
            axes.setTranslateX(i);

            StackPane.setAlignment(axes, Pos.CENTER_LEFT);
            StackPane.setMargin(axes, new Insets(20, 0, 0, 0));
            stackPane.getChildren().add(axes);

        }
        return stackPane;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
