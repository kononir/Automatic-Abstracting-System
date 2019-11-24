package by.bsuir.autoabstrsys.view;

import javafx.scene.control.Alert;

public class ErrorAlert {
    public void show(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(text);
        alert.show();
    }
}
