package trestview.routeperspective.schemaroute.mesh.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Arrays;

public class Util {

    public static void alignLabels(GridPane gridPane) {

        gridPane.getChildren()
                .forEach(children -> {
                    if (children instanceof Label) {
                        ((Label) children).prefWidthProperty().bind(gridPane.widthProperty());
                        ((Label) children).setAlignment(Pos.CENTER);
                    }
                });
    }

    public static String shortenOperationName(String operationName) {

        StringBuilder builder = new StringBuilder();

        String[] splitted = operationName.split(" ");


        /**
         * creates abbreviation from operation name
         */
        Arrays.stream(splitted)
                .forEach(word -> {
                    if (word.length() == 0) {
                        return;
                    }
                    builder.append(word.charAt(0));
                    for (int i = 1; i < word.length(); i++) {
                        if (Character.isDigit(word.charAt(i))) {
                            builder.append(word.charAt(i));
                        }
                    }
                });
        return builder.toString();
    }
}
