package by.bsuir.autoabstrsys.searcher;

import by.bsuir.autoabstrsys.model.OstisResponse;

public interface Searcher {
    OstisResponse search(String term);
}
