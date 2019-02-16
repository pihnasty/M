package dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertDialog extends Alert {

    private static AlertDialog alertDialog;

    private AlertDialog(AlertType alertType) {
        super(alertType);
    }

    private AlertDialog(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }

    public static void getAlert (String title, String message) {
        alertDialog = new AlertDialog(Alert.AlertType.INFORMATION);
        alertDialog.setTitle(title);
        alertDialog.setHeaderText(null);
        alertDialog.setContentText(message);
        alertDialog.showAndWait();
    }

    public static void getAlertShow (String title, String message) {
        alertDialog = new AlertDialog(Alert.AlertType.INFORMATION);
        alertDialog.setTitle(title);
        alertDialog.setHeaderText(null);
        alertDialog.setContentText(message);
        alertDialog.show();
    }

    public static boolean getAlertOkCancel(String title, String message,String  left,String right) {
        alertDialog = new AlertDialog(AlertType.WARNING);
        ButtonType leftButton = new ButtonType(left, ButtonBar.ButtonData.OK_DONE);
        ButtonType rightButton = new ButtonType(right, ButtonBar.ButtonData.CANCEL_CLOSE);
        alertDialog.getButtonTypes().setAll(leftButton,rightButton);
        alertDialog.setTitle(title);
        alertDialog.setHeaderText(null);
        alertDialog.setContentText(message);
        return alertDialog.showAndWait().get().equals(leftButton)?true:false;
    }

}
