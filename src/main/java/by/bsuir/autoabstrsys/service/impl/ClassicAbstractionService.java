package by.bsuir.autoabstrsys.service.impl;

import by.bsuir.autoabstrsys.service.AbstractAbstractionService;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ClassicAbstractionService extends AbstractAbstractionService {

    private static final String SENTENCE_SPLITTER = "[.?!]\\s*";

    @Override
    public List<String> makeAbstraction(String fileContent, List<String> fileContents) {
        Map<String, Double> sentencesMap = new LinkedHashMap<>();
        String fileContentToLowerCase = fileContent.toLowerCase();
        List<String> fileContentsToLowerCase = fileContents.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        String[] paragraphs = getParagraphs(fileContent);
        for(String paragraph: paragraphs) {
            String[] sentences = paragraph.split(SENTENCE_SPLITTER);
            for(String sentence: sentences) {
                String sentenceLowerCase = sentence.toLowerCase();
                double positionInDocument = sentencePosition(sentenceLowerCase, fileContentToLowerCase);
                double positionInParagraph = sentencePosition(sentenceLowerCase, paragraph);
                double score = calculateScore(sentenceLowerCase, fileContentToLowerCase, fileContentsToLowerCase);

                sentencesMap.put(sentence + '.', positionInParagraph * positionInDocument * score);
            }
        }

        return getMostFrequent(sentencesMap);
    }

    private double sentencePosition(String sentence, String text) {
        double totalSymbolsAmount = text.replaceAll("[.?!\\s\\d]+", "").length();
        double symbolsBeforeSentenceAmount = text.split(sentence)[0]
                .replaceAll("[.?!\\s\\d]+", "")
                .length();

        return 1 - (symbolsBeforeSentenceAmount/totalSymbolsAmount);
    }

    private String[] getParagraphs(String text) {
        return text.split("\t");
    }

    private double calculateScore(String sentence, String documentContent, List<String> fileContents) {
        double res = 0;
        Map<String, Double> termsSentenceFrequencyMap = calculateFrequencies(sentence);
        Map<String, Double> termsDocumentFrequencyMap = calculateFrequencies(documentContent);

        double maxFrequency = getMaxFrequency(termsDocumentFrequencyMap);

        for(Entry<String, Double> entry: termsSentenceFrequencyMap.entrySet()) {
            double frequencyInSentence = entry.getValue();

            double frequencyInDocument = termsDocumentFrequencyMap.get(entry.getKey());
            double documentAmount = fileContents.size();
            double documentContainsAmount = getDocAmountContainsTerm(entry.getKey(), fileContents);

            res += frequencyInSentence * 0.5 * (1 + frequencyInDocument/maxFrequency)
                    * Math.log(documentAmount/documentContainsAmount);
        }

        return res;
    }

    private double getDocAmountContainsTerm(String key, List<String> fileContents) {
        double res = 0;
        for(String doc: fileContents) {
            if(doc.contains(key)) {
                res++;
            }
        }

        return res;
    }

    private double getMaxFrequency(Map<String, Double> map){
        Optional<Entry<String, Double>> maxEntry = map.entrySet()
                .stream()
                .max(Comparator.comparing(Entry::getValue));
        return maxEntry.get()
                .getValue();
    }
}
