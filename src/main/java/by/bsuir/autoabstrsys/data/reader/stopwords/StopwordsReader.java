package by.bsuir.autoabstrsys.data.reader.stopwords;

import java.util.List;

public interface StopwordsReader {
    List<String> read(String fileName);
}
