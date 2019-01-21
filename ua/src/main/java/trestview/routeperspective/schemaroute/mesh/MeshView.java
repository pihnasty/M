package trestview.routeperspective.schemaroute.mesh;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import trestview.routeperspective.schemaroute.mesh.states.LargeStateBuilder;
import trestview.routeperspective.schemaroute.mesh.states.MediumStateBuilder;
import trestview.routeperspective.schemaroute.mesh.states.SmallStateBuilder;
import trestview.routeperspective.schemaroute.mesh.states.State;

import java.util.Observable;
import java.util.Observer;


//TODO: привести в человеческий вид. для плаката.
/**
 * MeshView class represents view of such entity as mesh on the scheme route canvas
 */
public class MeshView extends StackPane implements Observer {

    /**
     * MVC parts
     */
    private MeshModel meshModel;
    private MeshController meshController;

    /**
     * mesh coordinates scale and size (dimensional values)
     */
    public static final double MAX_WIDTH = 250;
    public static final double MAX_HEIGHT = 80;
    private Double x;
    private Double y;
    private Double scale;
    private GridPane gridPane;
    private Image image;

    /**
     * interface responsible for creating gridPane with different size patterns;
     */
    private State state;

    /**
     * initialize dimensional values and choose gridPaneBuilder mode
     * based on scale value
     *
     * @param x     coordinate of the mesh on the scheme
     * @param y     coordinate of the mesh on the scheme
     * @param scale of the mesh
     */
    public void initDimensions(Double scale, Double x, Double y) {

        Image imgWork = new Image("file:"+this.meshModel.getWork().getScheme());
        this.scale = scale;
        this.x = x * imgWork.getWidth() - (MAX_WIDTH * scale / 2);
        this.y = y * imgWork.getHeight() - (MAX_HEIGHT * scale / 2);
        this.setState(scale);
    }

    public MeshView(MeshModel meshModel, MeshController meshController) {

        this.meshModel = meshModel;
        this.meshController = meshController;

        initDimensions(
                .7,
                meshModel.getMachine().getLocationX(),
                meshModel.getMachine().getLocationY()
        );
//        appendTooltip();
        updateStackPane();
    }

    private void appendTooltip() {

        StringBuilder message = new StringBuilder();
        message.append("machine: ").append(meshModel.getMachine().getName()).append("\n")
                .append("min input buffer: ").append(meshModel.getMinInputBuffer()).append("\n")
                .append("max input buffer: ").append(meshModel.getMaxInputBuffer()).append("\n")
                .append("min output buffer: ").append(meshModel.getMinOutputBuffer()).append("\n")
                .append("max output buffer: ").append(meshModel.getMaxOutputBuffer()).append("\n")
                .append("operation: ").append(meshModel.getOperationName()).append("\n");
        if (meshModel.getSecondaryParams().size() > 0) {
            meshModel.getSecondaryParams()
                    .forEach((k, v) -> message.append(k).append(": ").append(v).append("\n"));
        }
        Tooltip tooltip = new Tooltip(message.toString());
        tooltip.setGraphic(new ImageView(meshModel.getMachineImage()));
        Tooltip.install(this, tooltip);
    }

    public MeshModel getMeshModel( ) {
        return meshModel;
    }

    public MeshController getMeshController( ) {
        return meshController;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public Double getX( ) {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY( ) {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public GridPane getGridPane( ) {
        return gridPane;
    }

    public void updateStackPane( ) {

        this.getChildren().clear();
        this.setMaxSize(MeshView.MAX_WIDTH * scale, MeshView.MAX_HEIGHT * scale);
        this.setTranslateX(this.x);
        this.setTranslateY(this.y);
        this.gridPane.getChildren()
                .forEach(cell -> cell.getStyleClass().add("grid_pane_cell"));
        this.getChildren().add(this.gridPane);
        this.getStyleClass().add("stack_pane_style");
    }

    public Double getScale( ) {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
        this.setState(scale);
    }

    public State getState( ) {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setState(Double scale) {

        if (scale <= .5) {
            state = new SmallStateBuilder();
        } else if (.5 < scale && scale < 1) {
            state = new MediumStateBuilder();
        } else if (scale >= 1) {
            state = new LargeStateBuilder();
        }
        this.gridPane = state.build(this);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (o.getClass() == MeshModel.class) {
            MeshModel model = (MeshModel) o;
            gridPane = state.build(this);
            updateStackPane();
        }
    }
}
