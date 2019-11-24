package by.bsuir.autoabstrsys.data.reader.stopwords.impl;

import by.bsuir.autoabstrsys.data.reader.JSONReaderException;
import by.bsuir.autoabstrsys.data.reader.stopwords.StopwordsReader;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONStopwordsReader implements StopwordsReader {
    @Override
    public List<String> read(String fileName) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            Reader reader = new InputStreamReader(inputStream);
            JSONArray array = (JSONArray) new JSONParser().parse(reader);
            reader.close();

            Iterator iterator = array.iterator();
            List<String> words = new ArrayList<>();
            while (iterator.hasNext()) {
                words.add((String) iterator.next());
            }

            return words;
        } catch (IOException | ParseException e) {
            throw new JSONReaderException("Error when read stopwords file with name " + fileName, e);
        }
    }
}
