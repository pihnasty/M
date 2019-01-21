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
public class MediumStateBuilder extends AbstractState {

    @Override
    public GridPane build(MeshView meshView) {

        this.meshView = meshView;
        GridPane gridPane;

        gridPane = new GridPane();
        gridPane.setPadding(new Insets(2));

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        ColumnConstraints column3 = new ColumnConstraints();
        column1.setPercentWidth(40);
        column2.setPercentWidth(40);
        column3.setPercentWidth(20);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        Label minInputBufferLabel = getMinInputBufferLabel();
        GridPane.setHalignment(minInputBufferLabel, HPos.CENTER);
        gridPane.add(minInputBufferLabel, 0, 0);

        Label maxInputBufferLabel = getMaxInputBufferLabel();
        GridPane.setHalignment(maxInputBufferLabel, HPos.CENTER);
        gridPane.add(maxInputBufferLabel, 1, 0);

        Label operationNameLabel = getOperationNameLabel();
        GridPane.setHalignment(operationNameLabel, HPos.CENTER);
        gridPane.add(operationNameLabel, 0, 1);
        GridPane.setColumnSpan(operationNameLabel, 2);

        Label minOutputBufferLabel = getMinOutputBufferLabel();
        GridPane.setHalignment(minOutputBufferLabel, HPos.CENTER);
        gridPane.add(minOutputBufferLabel, 0, 2);

        Label maxOutputBufferLabel = getMaxOutputBufferLabel();
        GridPane.setHalignment(maxOutputBufferLabel, HPos.CENTER);
        gridPane.add(maxOutputBufferLabel, 1, 2);

        ImageView machineImageView = getMachineImageView();
        GridPane.setHalignment(machineImageView, HPos.CENTER);
        gridPane.add(machineImageView, 2, 0);
        GridPane.setRowSpan(machineImageView, 3);

        Util.alignLabels(gridPane);

        meshView.setState(this);
        return gridPane;
    }
}
