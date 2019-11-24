package by.bsuir.autoabstrsys.service;

import by.bsuir.autoabstrsys.model.ResultData;

import java.io.File;
import java.util.List;

public interface AbstractionProviderService {
    List<ResultData> execute(List<File> files);
}
