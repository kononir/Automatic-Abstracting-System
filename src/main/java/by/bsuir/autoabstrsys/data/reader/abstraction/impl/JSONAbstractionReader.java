package by.bsuir.autoabstrsys.data.reader.abstraction.impl;

import by.bsuir.autoabstrsys.data.reader.JSONReaderException;
import by.bsuir.autoabstrsys.data.reader.abstraction.AbstractionReader;
import by.bsuir.autoabstrsys.data.writer.JSONWriterException;
import by.bsuir.autoabstrsys.model.ResultData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONAbstractionReader implements AbstractionReader {
    @Override
    public List<ResultData> read(File file) {
        try {
            Reader reader = new FileReader(file);
            JSONArray array = (JSONArray) new JSONParser().parse(reader);
            reader.close();

            Iterator iterator = array.iterator();
            List<ResultData> results = new ArrayList<>();
            while (iterator.hasNext()) {
                JSONObject result = (JSONObject) iterator.next();

                String filePath = (String) result.get("filePath");

                JSONArray sentencesArray = (JSONArray) result.get("sentences");
                Iterator sentencesIt = sentencesArray.iterator();
                List<String> sentences = new ArrayList<>();
                while (sentencesIt.hasNext()) {
                    sentences.add((String) sentencesIt.next());
                }

                JSONArray keywordsArray = (JSONArray) result.get("keywords");
                Iterator keywordsIt = keywordsArray.iterator();
                List<String> keywords = new ArrayList<>();
                while (keywordsIt.hasNext()) {
                    keywords.add((String) keywordsIt.next());
                }

                results.add(new ResultData(filePath, sentences, keywords));
            }

            return results;
        } catch (IOException | ParseException e) {
            throw new JSONReaderException("Error when read json model from file with name " + file.getName(), e);
        }
    }
}
