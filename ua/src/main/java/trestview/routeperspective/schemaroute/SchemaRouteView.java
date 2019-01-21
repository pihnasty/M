package trestview.routeperspective.schemaroute;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.StackPaneObserverDS;
import entityProduction.Work;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import trestview.routeperspective.schemaroute.mesh.MeshView;

import java.util.List;
import java.util.Observable;

public class SchemaRouteView extends StackPaneObserverDS {

    private ImageView imageview = new ImageView();
    private Work work;
    private Double scaleScheme;

    private StackPane stackPaneTop;
    private StackPane stackPaneBottom;

    private SplitPane splitPane;
    private ScrollPane scrollPane;

    private DoubleBinding kScale;
    private DoubleBinding hImv;
    private DoubleBinding wImv;
    private List<MeshView> meshes;
    private ImageView meshInfo = new ImageView();

    public SchemaRouteView(ObservableDS observableDS, InitializableDS initializableDS) {
        super(observableDS, initializableDS);


        /* Stack Panes Init */
        stackPaneTop = new StackPane();
        stackPaneTop.setAlignment(Pos.TOP_LEFT);
        stackPaneBottom = new StackPane();
        stackPaneBottom.setAlignment(Pos.CENTER);

        /* Scroll Pane Init */
        scrollPane = new ScrollPane();
        scrollPane.setContent(stackPaneTop);

        /* Split Pane Init */
        splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPositions(0.75, 0.25);
        splitPane.getItems().addAll(scrollPane, stackPaneBottom);


        getChildren().addAll(stackPaneTop);
        meshes = ((SchemaRouteModel) observableDS).getMeshes();
        setStyle("-fx-background-color: #336699;");

        this.getChildren().add(splitPane);

        setkScaleDefault();
        repaint((SchemaRouteModel) this.observableDS);
//        stackPaneTop.scaleXProperty().bind(kScale);
//        stackPaneTop.scaleYProperty().bind(kScale);
    }

    private void repaint(SchemaRouteModel schemaModel) {

        this.stackPaneTop.getChildren().clear();

        this.work = schemaModel.getWork();
        this.imageview.setImage(new Image("file:" + work.getScheme()));

        meshInfo.setImage(new Image("file:Image/MeshInfo.jpg"));
        meshInfo.setTranslateX(imageview.getImage().getWidth());
        meshInfo.setTranslateY(0);

        if (imageview.getImage() != null) {
            stackPaneTop.getChildren().add(imageview);
        }

        if (meshes != null) {
            stackPaneTop.getChildren().addAll(meshes);
        }

        if (meshInfo != null) {
            stackPaneTop.getChildren().add(meshInfo);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (((SchemaRouteModel) o).getMouseEvent() != null) {
            if (((SchemaRouteModel) o).getMouseEvent().getEventType() == MouseEvent.MOUSE_MOVED) {
                setCursor(((SchemaRouteModel) o).getCursor());
                return;
            }
            if (((SchemaRouteModel) o).getMouseEvent().getEventType() == MouseEvent.MOUSE_PRESSED) {
                meshes = ((SchemaRouteModel) o).getMeshes();
                return;
            }
        }
        repaint((SchemaRouteModel) o);
        setHeight(getHeight() + 1);
        setHeight(getHeight() - 1);

    }

    private void setkScaleDefault( ) {
        kScale = new DoubleBinding() {
            {
                super.bind(heightProperty());
            }

            @Override
            protected double computeValue( ) {
                if (observableDS instanceof SchemaRouteModel)
                    ((SchemaRouteModel) observableDS).setkScale(0.85 * getHeight() / imageview.getImage().getHeight());
                return 0.85 * getHeight() / imageview.getImage().getHeight();
            }
        };
    }

}
