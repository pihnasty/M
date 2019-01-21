package trestview.routeperspective.schemaroute.mesh.states;

import javafx.scene.layout.GridPane;
import trestview.routeperspective.schemaroute.mesh.MeshView;

public interface State {
    GridPane build(MeshView meshView);
}
