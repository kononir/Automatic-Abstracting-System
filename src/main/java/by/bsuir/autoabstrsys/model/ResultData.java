package by.bsuir.autoabstrsys.model;

import java.util.List;

public class ResultData {
    private String filePath;
    private List<String> sentences;
    private List<String> keywords;

    public ResultData(String filePath, List<String> sentences, List<String> keywords) {
        this.filePath = filePath;
        this.sentences = sentences;
        this.keywords = keywords;
    }

    public String getFilePath() {
        return filePath;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}
