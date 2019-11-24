package by.bsuir.autoabstrsys.controller;

import by.bsuir.autoabstrsys.model.ResultData;
import by.bsuir.autoabstrsys.view.ResultsPrinter;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.List;

public class LoadedResultsController extends AbstractController {
    private static final String HELP_FXML_FILE_PATH = "view/loaded_results_help.fxml";

    @FXML
    public VBox resultsVBox;

    private List<ResultData> documentsStemResults;

    public void setDocumentsStemResults(List<ResultData> documentsStemResults) {
        this.documentsStemResults = documentsStemResults;
    }

    public void controlShowingLoadedResults() {
        new ResultsPrinter().print(documentsStemResults, resultsVBox);
    }

    @FXML
    private void controlShowingHelp() {
        super.controlShowingHelp(HELP_FXML_FILE_PATH);
    }
}
