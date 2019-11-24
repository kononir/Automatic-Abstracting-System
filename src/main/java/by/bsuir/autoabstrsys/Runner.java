package by.bsuir.autoabstrsys;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Runner extends Application {
    private static final String MAIN_FXML_FILE_PATH = "view/main.fxml";

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(MAIN_FXML_FILE_PATH));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Automatic Abstraction System");
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
    }
}
