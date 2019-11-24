package by.bsuir.autoabstrsys.controller;

import by.bsuir.autoabstrsys.data.writer.AbstractionWriter;
import by.bsuir.autoabstrsys.data.writer.impl.JSONAbstractionWriter;
import by.bsuir.autoabstrsys.model.ResultData;
import by.bsuir.autoabstrsys.view.ResultsPrinter;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class ResultsController extends AbstractController {
    private static final String HELP_FXML_FILE_PATH = "view/results_help.fxml";

    private AbstractionWriter abstractionWriter = new JSONAbstractionWriter();

    @FXML
    public VBox resultsVBox;
    @FXML
    public Text stemmingTime;
    @FXML
    public VBox root;

    private List<ResultData> documentsStemResults;
    private int time;

    public void setDocumentsStemResults(List<ResultData> documentsStemResults) {
        this.documentsStemResults = documentsStemResults;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void controlShowingResults() {
        stemmingTime.setText("Abstraction making time: " + time + "ms");

        new ResultsPrinter().print(documentsStemResults, resultsVBox);
    }

    @FXML
    private void controlSaveToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File savingFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        if (savingFile != null) {
            abstractionWriter.write(documentsStemResults, savingFile);
        }
    }

    @FXML
    private void controlShowingHelp() {
        super.controlShowingHelp(HELP_FXML_FILE_PATH);
    }
}
