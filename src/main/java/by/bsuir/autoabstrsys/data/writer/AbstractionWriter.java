package by.bsuir.autoabstrsys.data.writer;

import by.bsuir.autoabstrsys.model.ResultData;

import java.io.File;
import java.util.List;

public interface AbstractionWriter {
    void write(List<ResultData> results, File file);
}
