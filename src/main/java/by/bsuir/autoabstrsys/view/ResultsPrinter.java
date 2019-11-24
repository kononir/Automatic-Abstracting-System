package by.bsuir.autoabstrsys.view;

import by.bsuir.autoabstrsys.model.ResultData;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResultsPrinter {
    public void print(List<ResultData> documentsStemResults, VBox resultsVBox) {
        VBox documentsVBox = new VBox();
        documentsVBox.setId("document-results");
        for (ResultData result : documentsStemResults) {
            VBox documentVBox = new VBox();
            documentVBox.setId("result");
            documentsVBox.getChildren().add(documentVBox);

            Text documentPathText = new Text(result.getFilePath());
            documentPathText.setId("active-link");
            documentPathText.setOnMouseClicked(event -> openFile(result.getFilePath()));
            documentVBox.getChildren().add(documentPathText);

            VBox classicVBox = new VBox();
            Text classicText = new Text("CLASSIC:");
            classicVBox.getChildren().add(classicText);
            for (String sentence : result.getSentences()) {
                Text sentenceText = new Text(" * " + sentence);
                classicVBox.getChildren().add(sentenceText);
            }
            documentVBox.getChildren().add(classicVBox);

            VBox keywordsVBox = new VBox();
            Text keywordsText = new Text("KEYWORDS:");
            keywordsVBox.getChildren().add(keywordsText);
            for (String keyword : result.getKeywords()) {
                Text keywordText = new Text(" - " + keyword);
                keywordsVBox.getChildren().add(keywordText);
            }
            documentVBox.getChildren().add(keywordsVBox);

            resultsVBox.getChildren().add(documentVBox);
        }
    }

    private void openFile(String path) {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException e) {
            new ErrorAlert().show("Unable to open file");
        }
    }
}
