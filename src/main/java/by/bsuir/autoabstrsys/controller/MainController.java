package by.bsuir.autoabstrsys.controller;

import by.bsuir.autoabstrsys.data.reader.abstraction.AbstractionReader;
import by.bsuir.autoabstrsys.data.reader.abstraction.impl.JSONAbstractionReader;
import by.bsuir.autoabstrsys.model.ResultData;
import by.bsuir.autoabstrsys.searcher.Searcher;
import by.bsuir.autoabstrsys.searcher.impl.SearcherImpl;
import by.bsuir.autoabstrsys.service.AbstractionProviderService;
import by.bsuir.autoabstrsys.service.impl.AbstractionProviderServiceImpl;
import by.bsuir.autoabstrsys.service.impl.ClassicAbstractionService;
import by.bsuir.autoabstrsys.service.impl.KeywordsAbstractionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MainController extends AbstractController {
    private static final int MILLIS_FROM_NANO = 1000000;

    private static final String RESULTS_FXML_FILE_PATH = "view/results.fxml";
    private static final String LOADED_RESULTS_FXML_FILE_PATH = "view/loaded_results.fxml";
    private static final String HELP_FXML_FILE_PATH = "view/main_help.fxml";

    private ClassicAbstractionService classicAbstractionService = new ClassicAbstractionService();
    private Searcher searcher = new SearcherImpl();
    private KeywordsAbstractionService keyWordsAbstractionService = new KeywordsAbstractionService(searcher);
    private AbstractionProviderService abstractionProviderService
            = new AbstractionProviderServiceImpl(classicAbstractionService, keyWordsAbstractionService);

    private AbstractionReader abstractionReader = new JSONAbstractionReader();

    @FXML
    private VBox root;

    @FXML
    private void controlPressingButton(KeyEvent event) {
        if (KeyCode.ENTER.equals(event.getCode())) {
            controlMakingAbstraction();
        }
    }

    @FXML
    private void controlMakingAbstraction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        List<File> chosenStemmingFiles = fileChooser.showOpenMultipleDialog(root.getScene().getWindow());

        if (chosenStemmingFiles != null) {
            try {
                int timeBeforeStem = LocalDateTime.now().getNano() / MILLIS_FROM_NANO;
                List<ResultData> documentsAbstractionResults = abstractionProviderService.execute(chosenStemmingFiles);
                int timeAfterSearch = LocalDateTime.now().getNano() / MILLIS_FROM_NANO;

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource(RESULTS_FXML_FILE_PATH));
                Parent root = loader.load();

                ResultsController resultsController = loader.getController();
                resultsController.setDocumentsStemResults(documentsAbstractionResults);
                resultsController.setTime(Math.abs(timeAfterSearch - timeBeforeStem));
                resultsController.controlShowingResults();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Abstraction results");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void controlLoadingResults() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            try {
                List<ResultData> results = abstractionReader.read(file);

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource(LOADED_RESULTS_FXML_FILE_PATH));
                Parent root = loader.load();

                LoadedResultsController controller = loader.getController();
                controller.setDocumentsStemResults(results);
                controller.controlShowingLoadedResults();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Loaded abstraction results");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void controlShowingHelp() {
        super.controlShowingHelp(HELP_FXML_FILE_PATH);
    }
}
