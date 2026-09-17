package com.vityarthi.resumescreener.nlp;

import java.util.*;
import java.util.regex.Pattern;

public class TextProcessor {
    private static final Pattern NON_WORD = Pattern.compile("[^a-z0-9+#. ]");
    private static final Set<String> STOP = Set.of("a","an","and","as","at","by","for","from","in","is","of","on","or","the","to","with");

    public List<String> tokens(String text) {
        String cleaned = NON_WORD.matcher(text.toLowerCase(Locale.ROOT).replace('-', ' ')).replaceAll(" ");
        return Arrays.stream(cleaned.split("\\s+")).filter(s -> s.length() > 1 && !STOP.contains(s)).toList();
    }

    public Map<String, Double> termFrequency(String text) {
        List<String> tokens = tokens(text);
        Map<String, Double> result = new HashMap<>();
        for (String token : tokens) result.merge(token, 1.0, Double::sum);
        if (!tokens.isEmpty()) result.replaceAll((k, v) -> v / tokens.size());
        return result;
    }

    public double cosineSimilarity(String first, String second) {
        Map<String, Double> a = termFrequency(first), b = termFrequency(second);
        Set<String> terms = new HashSet<>(a.keySet()); terms.addAll(b.keySet());
        double dot = 0, normA = 0, normB = 0;
        for (String term : terms) { double x = a.getOrDefault(term, 0.0), y = b.getOrDefault(term, 0.0); dot += x * y; normA += x*x; normB += y*y; }
        return normA == 0 || normB == 0 ? 0 : dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
