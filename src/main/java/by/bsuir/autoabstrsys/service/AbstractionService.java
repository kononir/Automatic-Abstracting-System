package by.bsuir.autoabstrsys.service;

import java.util.List;

public interface AbstractionService {
    List<String> makeAbstraction(String fileContent, List<String> fileContents);
}
