package by.bsuir.autoabstrsys.service;

import by.bsuir.autoabstrsys.data.reader.stopwords.impl.JSONStopwordsReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractAbstractionService implements AbstractionService {
    private static final String STOPWORDS_FILE = "stopwords.json";

    private final Set<String> STOP_WORDS = getStopWords();

    protected Map<String, Double> calculateFrequencies(String text) {
        Map<String, Double> result = new LinkedHashMap<>();
        Pattern pattern = Pattern.compile("[^\\w]", Pattern.UNICODE_CHARACTER_CLASS);
        String[] terms = pattern.split(text);

        for (String term : terms) {
            if(!STOP_WORDS.contains(term) && term.length() >= 1){
                if (result.containsKey(term)) {
                    double frequency = result.get(term);
                    result.put(term, ++frequency);
                } else {
                    result.put(term, 1.0);
                }
            }
        }

        return result;
    }

    private Set<String> getStopWords() {
        return new HashSet<>(new JSONStopwordsReader().read(STOPWORDS_FILE));
    }

    protected List<String> getMostFrequent(Map<String, Double> map) {
        List<Map.Entry<String, Double>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        Collections.reverse(entries);
        return entries
                .stream()
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
    }
}
