package by.bsuir.autoabstrsys.data.writer.impl;

import by.bsuir.autoabstrsys.data.writer.AbstractionWriter;
import by.bsuir.autoabstrsys.data.writer.JSONWriterException;
import by.bsuir.autoabstrsys.model.ResultData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONAbstractionWriter implements AbstractionWriter {

    public void write(List<ResultData> results, File file) {
        JSONArray array = new JSONArray();
        for (ResultData result : results) {
            JSONObject fileResultObject = new JSONObject();
            array.add(fileResultObject);

            fileResultObject.put("filePath", result.getFilePath());

            JSONArray sentencesArray = new JSONArray();
            sentencesArray.addAll(result.getSentences());
            fileResultObject.put("sentences", sentencesArray);

            JSONArray keywordsArray = new JSONArray();
            keywordsArray.addAll(result.getKeywords());
            fileResultObject.put("keywords", keywordsArray);
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(array.toJSONString());
            writer.flush();
        } catch (IOException e) {
            throw new JSONWriterException("Error when write json model to file with name " + file, e);
        }
    }
}
