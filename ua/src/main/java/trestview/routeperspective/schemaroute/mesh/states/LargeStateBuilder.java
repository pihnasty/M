package trestview.routeperspective.schemaroute.mesh.states;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import trestview.routeperspective.schemaroute.mesh.MeshView;
import trestview.routeperspective.schemaroute.mesh.utils.Util;

import java.util.List;

/**
 * Created by lanz on 24.03.2017.
 */
public class LargeStateBuilder extends AbstractState {

    @Override
    public GridPane build(MeshView meshView) {

        this.meshView = meshView;
        GridPane gridPane;

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(5));

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(column1, column2);

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

        /*
         * used as a wrap for int value of counter. We are not allowed
         * to user changeable values inside lambda expressions
         * hence we use this wrapper
         */
        class Counter {

            private int counter;

            private Counter(int startFrom) {
                counter = startFrom;
            }

            private void increaseCounter( ) {
                counter++;
            }

            private int getCounter( ) {
                return counter;
            }
        }

        List<Label> secondaryParamLabels = getSecondaryParamLabels();
        Counter counter = new Counter(2);
        /*
         * for each secondary label creates additional row with specific value
         */
        secondaryParamLabels.stream().forEach(label -> {
            GridPane.setHalignment(label, HPos.CENTER);
            gridPane.add(label, 0, counter.getCounter());
            counter.increaseCounter();
            GridPane.setColumnSpan(label, 2);
        });

        Label minOutputBufferLabel = getMinOutputBufferLabel();
        GridPane.setHalignment(minOutputBufferLabel, HPos.CENTER);
        gridPane.add(minOutputBufferLabel, 0, counter.getCounter());

        Label maxOutputBufferLabel = getMaxOutputBufferLabel();
        GridPane.setHalignment(maxOutputBufferLabel, HPos.CENTER);
        gridPane.add(maxOutputBufferLabel, 1, counter.getCounter());

        ImageView machineImageView = getMachineImageView();
        GridPane.setHalignment(machineImageView, HPos.CENTER);
        gridPane.add(machineImageView, 2, 0);
        GridPane.setRowSpan(machineImageView, 3);

        Util.alignLabels(gridPane);

        meshView.setState(this);
        return gridPane;
    }
}
