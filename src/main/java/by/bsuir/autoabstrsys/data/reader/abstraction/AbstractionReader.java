package by.bsuir.autoabstrsys.data.reader.abstraction;

import by.bsuir.autoabstrsys.model.ResultData;

import java.io.File;
import java.util.List;

public interface AbstractionReader {
    List<ResultData> read(File file);
}
