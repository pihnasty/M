package trestview.routeperspective.schemaroute.mesh.states;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import trestview.routeperspective.schemaroute.mesh.MeshView;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractState implements State {

    MeshView meshView;

    Label getMinInputBufferLabel( ) {

        int minInputBuffer = meshView.getMeshModel().getMinInputBuffer();
        Label minInputBufferLabel = new Label(String.valueOf(minInputBuffer));
        Tooltip minInputBufferTooltip = new Tooltip("Min Input Buffer: " + minInputBuffer);
        Tooltip.install(minInputBufferLabel, minInputBufferTooltip);
        return minInputBufferLabel;
    }

    Label getMaxInputBufferLabel( ) {

        int maxInputBuffer = meshView.getMeshModel().getMaxInputBuffer();
        Label maxInputBufferLabel = new Label(String.valueOf(maxInputBuffer));
        Tooltip maxInputBufferTooltip = new Tooltip("Max Input Buffer: " + maxInputBuffer);
        Tooltip.install(maxInputBufferLabel, maxInputBufferTooltip);
        return maxInputBufferLabel;
    }

    Label getMinOutputBufferLabel( ) {

        int minOutputBuffer = meshView.getMeshModel().getMinOutputBuffer();
        Label minOutputBufferLabel = new Label(String.valueOf(minOutputBuffer));
        Tooltip minOutputBufferTooltip = new Tooltip("Min Output Buffer: " + minOutputBuffer);
        Tooltip.install(minOutputBufferLabel, minOutputBufferTooltip);
        return minOutputBufferLabel;
    }

    Label getMaxOutputBufferLabel( ) {

        int maxOutputBuffer = meshView.getMeshModel().getMaxOutputBuffer();
        Label maxOutputBufferLabel = new Label(String.valueOf(maxOutputBuffer));
        Tooltip maxOutputBufferTooltip = new Tooltip("Max Output Buffer: " + maxOutputBuffer);
        Tooltip.install(maxOutputBufferLabel, maxOutputBufferTooltip);
        return maxOutputBufferLabel;
    }

    Label getOperationNameLabel( ) {

        String operationName = meshView.getMeshModel().getOperationName();
        Label operationNameLabel = new Label(operationName);
        Tooltip operationNameTooltip = new Tooltip("Operation name: " + operationName);
        Tooltip.install(operationNameLabel, operationNameTooltip);
        return operationNameLabel;
    }

    List<Label> getSecondaryParamLabels( ) {
        List<Label> secondaryParamLabels = new ArrayList<>();
        meshView.getMeshModel().getSecondaryParams()
                .forEach(
                        (k, v) -> {
                            Label currentLabel = new Label(v.toString());
                            Tooltip currentTooltip = new Tooltip(k + ": " + v.toString());
                            Tooltip.install(currentLabel, currentTooltip);
                            secondaryParamLabels.add(currentLabel);
                        }
                );
        return secondaryParamLabels;
    }

    ImageView getMachineImageView( ) {

        ImageView machineImageView = new javafx.scene.image.ImageView(meshView.getMeshModel().getMachineImage());
        machineImageView.setFitHeight(MeshView.MAX_HEIGHT * meshView.getScale());
        machineImageView.setFitWidth(MeshView.MAX_WIDTH * meshView.getScale() * .2);
        machineImageView.setPreserveRatio(true);
        Tooltip machineImageTooltip = new Tooltip("Image of: " + meshView.getMeshModel().getMachine().getName());
        machineImageTooltip.setGraphic(new javafx.scene.image.ImageView(meshView.getMeshModel().getMachineImage()));
        Tooltip.install(machineImageView, machineImageTooltip);
        return machineImageView;
    }
}
