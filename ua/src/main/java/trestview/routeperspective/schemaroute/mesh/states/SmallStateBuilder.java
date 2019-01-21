package trestview.routeperspective.schemaroute.mesh.states;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import trestview.routeperspective.schemaroute.mesh.MeshView;
import trestview.routeperspective.schemaroute.mesh.utils.Util;

/**
 * Created by lanz on 24.03.2017.
 */
public class SmallStateBuilder extends AbstractState {

    @Override
    public GridPane build(MeshView meshView) {

        this.meshView = meshView;
        GridPane gridPane;

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(1));

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(column1, column2);

        Label operationNameLabel = getOperationNameLabel();
        GridPane.setHalignment(operationNameLabel, HPos.CENTER);
        gridPane.add(operationNameLabel, 0, 0);

        ImageView machineImageView = getMachineImageView();
        GridPane.setHalignment(machineImageView, HPos.CENTER);
        gridPane.add(machineImageView, 1, 0);

        Util.alignLabels(gridPane);

        meshView.setState(this);
        return gridPane;
    }
}
