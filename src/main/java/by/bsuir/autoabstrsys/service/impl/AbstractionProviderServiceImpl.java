package by.bsuir.autoabstrsys.service.impl;

import by.bsuir.autoabstrsys.model.ResultData;
import by.bsuir.autoabstrsys.service.AbstractionProviderService;
import by.bsuir.autoabstrsys.service.AbstractionService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AbstractionProviderServiceImpl implements AbstractionProviderService {
    private final AbstractionService classicAbstractionService;
    private final AbstractionService keyWordsAbstractionService;

    public AbstractionProviderServiceImpl(AbstractionService classicAbstractionService,
                                          AbstractionService keyWordsAbstractionService){
        this.classicAbstractionService = classicAbstractionService;
        this.keyWordsAbstractionService = keyWordsAbstractionService;
    }

    @Override
    public List<ResultData> execute(List<File> files) {
        List<String> filesContentList = files.stream().map(this::getFileContent).collect(Collectors.toList());
        return files.stream().map(file -> {
            String content = getFileContent(file);

            List<String> classic = classicAbstractionService.makeAbstraction(content, filesContentList);

            List<String> keywords = keyWordsAbstractionService.makeAbstraction(content, filesContentList);

            return new ResultData(file.getPath(), classic, keywords);
        }).collect(Collectors.toList());
    }

    private String getFileContent(File file) {
        try {
            String fileContent = new String(Files.readAllBytes(file.toPath()));
            Pattern pattern = Pattern.compile("[^\\w0-9.?!:,\\-\\s\\t]", Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher(fileContent);
            return matcher.replaceAll("");
        } catch (IOException e) {
            return "";
        }
    }
}
