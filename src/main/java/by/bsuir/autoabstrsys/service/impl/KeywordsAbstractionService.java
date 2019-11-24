package by.bsuir.autoabstrsys.service.impl;

import by.bsuir.autoabstrsys.model.OstisResponse;
import by.bsuir.autoabstrsys.searcher.Searcher;
import by.bsuir.autoabstrsys.service.AbstractAbstractionService;

import java.util.*;
import java.util.stream.Collectors;

public class KeywordsAbstractionService extends AbstractAbstractionService {
    private Searcher searcher;

    public KeywordsAbstractionService(Searcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public List<String> makeAbstraction(String fileContent, List<String> fileContentList) {
        Map<String, Double> freqInCurrent = calculateFrequencies(fileContent);

        List<Map<String, Double>> allFrequencies = fileContentList.stream()
                .map(this::calculateFrequencies)
                .collect(Collectors.toList());

        Map<String, Double> frequenciesMap = getFrequencies(freqInCurrent, allFrequencies);

        List<String> mostFrequent = getMostFrequent(frequenciesMap);

        return mostFrequent.stream()
                .filter(rec -> !rec.isEmpty())
                .map(rec -> getKeyWords(rec, fileContent))
                .filter(rec -> !rec.isEmpty())
                .collect(Collectors.toList());
    }

    private String getKeyWords(String term, String fileContent) {
        OstisResponse response = searcher.search(term);

        StringBuilder stringBuilder = new StringBuilder();

        List<Object[]> sys = response.getSys();
        List<Object[]> main = response.getMain();
        List<Object[]> common = response.getCommon();

        if(!sys.isEmpty() || !main.isEmpty() || !common.isEmpty()) {
            stringBuilder.append(term);

            appendFromIdentifiersSetIfPresent(sys, fileContent, stringBuilder);
            appendFromIdentifiersSetIfPresent(main, fileContent, stringBuilder);
            appendFromIdentifiersSetIfPresent(common, fileContent, stringBuilder);
        }

        return stringBuilder.toString();
    }

    private void appendFromIdentifiersSetIfPresent(List<Object[]> identifiers, String fileContent, StringBuilder sb) {
        identifiers.forEach(objects -> {
            String identifier = objects[1].toString();
            if(fileContent.contains(identifier)) {
                sb.append(" = ").append(identifier);
            }
        });
    }

    private Map<String, Double> getFrequencies(Map<String, Double> current, List<Map<String, Double>> allFrequencies) {
        Map<String, Double> result = new HashMap<>(current.size());

        Set<String> words = current.keySet();

        words.forEach(word -> {
            double numerator = calculateNumber(word, current, allFrequencies);

            double denominator = 0;
            for(Map<String, Double> map : allFrequencies) {
                denominator += calculateNumber(word,map, allFrequencies);
            }

            result.put(word, numerator/denominator);
        });

        return result;
    }

    private double calculateNumber(String word, Map<String, Double> map, List<Map<String, Double>> totalMap) {
        return (map.containsKey(word) ? map.get(word) : 0) * Math.log(totalMap.size()/numOfDocsContainingWord(word, totalMap));
    }

    private double numOfDocsContainingWord(String word, List<Map<String, Double>> maps) {
        double res = 0;

        for(Map map: maps) {
            if(map.containsKey(word)) {
                res++;
            }
        }

        return res;
    }

}
