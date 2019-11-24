package by.bsuir.autoabstrsys;

import by.bsuir.autoabstrsys.model.ResultData;
import by.bsuir.autoabstrsys.searcher.Searcher;
import by.bsuir.autoabstrsys.searcher.impl.SearcherImpl;
import by.bsuir.autoabstrsys.service.AbstractionProviderService;
import by.bsuir.autoabstrsys.service.impl.AbstractionProviderServiceImpl;
import by.bsuir.autoabstrsys.service.impl.ClassicAbstractionService;
import by.bsuir.autoabstrsys.service.impl.KeywordsAbstractionService;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class ConsoleRunner {
    public static void main(String[] args) {
        File file = new File(args[0]);

        ClassicAbstractionService classicAbstractionService = new ClassicAbstractionService();
        Searcher searcher = new SearcherImpl();
        KeywordsAbstractionService keyWordsAbstractionService = new KeywordsAbstractionService(searcher);
        AbstractionProviderService abstractionProviderService
                = new AbstractionProviderServiceImpl(classicAbstractionService, keyWordsAbstractionService);

        List<ResultData> results = abstractionProviderService.execute(Collections.singletonList(file));
        System.out.println("Results:");
        for (ResultData result : results) {
            System.out.println(" * " + file.getName());
            System.out.println("\tCLASSIC:");
            for (String sentence : result.getSentences()) {
                System.out.println("\t" + sentence);
            }
            System.out.println("\n\tKEYWORDS:");
            for (String keyword : result.getKeywords()) {
                System.out.println("\t- " + keyword);
            }
        }
    }
}
