package trestview.orderplaninigperspective.schemaroute;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.StackPaneObserverDS;
import entityProduction.Work;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import trestview.orderplaninigperspective.tables.MyView;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class SchemaOrderPlaninigView extends StackPaneObserverDS {
    private ScrollPane scrollPane = new ScrollPane();
    private ImageView imageview = new ImageView();
    private Work work;
    private Double scaleScheme;

    private BorderPane bp;

    private DoubleBinding kScale;
    private DoubleBinding hImv;
    private DoubleBinding wImv;
//    private List<MyView> qs;

    //TODO there is an data in the observableDS
    //TODO paint all shit
    public SchemaOrderPlaninigView(ObservableDS observableDS, InitializableDS initializableDS) {
        super(observableDS, initializableDS);
        bp = new BorderPane();
        getChildren().add(scrollPane);
        scrollPane.setContent(bp);
//        getChildren().addAll(bp);
//        qs = ((SchemaOrderPlaninigModel)observableDS).getQs();
        setStyle("-fx-background-color: #336699;");

        setkScaleDefault();
        repaint((SchemaOrderPlaninigModel) this.observableDS);
        //TODO debug
/*        bp.scaleXProperty().bind(kScale);
        bp.scaleYProperty().bind(kScale);*/


//        qs = ((SchemaOrderPlaninigModel) observableDS).getTablePlusGraphic();
    }

    private void repaint(SchemaOrderPlaninigModel schemaModel) {
        this.bp.getChildren().clear();
        this.work = schemaModel.getWork();


        //TODO debug
//        this.imageview.setImage(new Image("file:"+work.getScheme() ));
        /*for(Q q: schemaModel.()) {
            q.setLayoutX(q.getX());
            q.setLayoutY(q.getY());
        }*/
//        if (imageview != null)           bp.getChildren().addAll(imageview);
        if (schemaModel.getTablePlusGraphic() != null) {

            if (!schemaModel.getTablePlusGraphic().isEmpty()) {
                schemaModel.getTablePlusGraphic().get(0).setLayoutY(100);
                bp.getChildren().add(schemaModel.getTablePlusGraphic().get(0));
            }

            for (int i = 1; i < schemaModel.getTablePlusGraphic().size(); i++) {
                schemaModel.getTablePlusGraphic().get(i).setLayoutY(bp.getChildren().get(i - 1).getLayoutY() + 150);

                bp.getChildren().add(schemaModel.getTablePlusGraphic().get(i));
            }
            //todo paint here all works
        }
  /*      Rectangle rect = new Rectangle(10, 10, TimeUnit.MILLISECONDS.toDays(1000000000), 25);
        rect.setFill(Color.BLACK);
        rect.setLayoutX(100);
        rect.setLayoutY(500);


        bp.getChildren().add(rect);

        if (bp.getChildren() != null) {
            int count = ((MyView) bp.getChildren().get(bp.getChildren().size() - 1)).getRowCount();
            ((MyView) bp.getChildren().get(bp.getChildren().size() - 1)).getGridPane().add(rect, 1, count);
        }*/
    }

    @Override
    public void update(Observable o, Object arg) {

        if (((SchemaOrderPlaninigModel) o).getMouseEvent() != null) {
            if (((SchemaOrderPlaninigModel) o).getMouseEvent().getEventType() == MouseEvent.MOUSE_MOVED)
                setCursor(((SchemaOrderPlaninigModel) o).getCursor());
            if (((SchemaOrderPlaninigModel) o).getMouseEvent().getEventType() == MouseEvent.MOUSE_PRESSED) {
//                qs = ((SchemaOrderPlaninigModel) o).getImems();
            }
        }
        repaint((SchemaOrderPlaninigModel) o);
        setHeight(getHeight() + 1);
        setHeight(getHeight() - 1);
    }

    private void setkScaleDefault() {
        kScale = new DoubleBinding() {
            {
                super.bind(heightProperty());
            }

            @Override
            protected double computeValue() {
                if (observableDS instanceof SchemaOrderPlaninigModel)
                    ((SchemaOrderPlaninigModel) observableDS).setkScale(0.85 * getHeight() / imageview.getImage().getHeight());
                return 0.85 * getHeight() / imageview.getImage().getHeight();
            }
        };
    }

}
