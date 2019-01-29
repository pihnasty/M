package dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertDialog extends Alert {

    private static AlertDialog alertDialog;

    private AlertDialog(AlertType alertType) {
        super(alertType);
    }

    private AlertDialog(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }

    public static void getAlert(String title, String message) {
        alertDialog = new AlertDialog(Alert.AlertType.INFORMATION);
        alertDialog.setTitle(title);
        alertDialog.setHeaderText(null);
        alertDialog.setContentText(message);
        alertDialog.showAndWait();
    }
}
