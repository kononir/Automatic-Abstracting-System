package by.bsuir.autoabstrsys.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractController {
    protected void controlShowingHelp(String helpPage) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(helpPage));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Help");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
