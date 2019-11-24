package by.bsuir.autoabstrsys.searcher;

public class SearcherException extends RuntimeException {
    public SearcherException(String message) {
        super(message);
    }

    public SearcherException(String message, Throwable cause) {
        super(message, cause);
    }
}
