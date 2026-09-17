package com.vityarthi.resumescreener.nlp;

import java.util.*;

public class SkillExtractor {
    private static final Map<String, List<String>> ALIASES = Map.ofEntries(
            Map.entry("java", List.of("java")), Map.entry("oop", List.of("oop", "object oriented programming")),
            Map.entry("collections", List.of("collections", "collection framework")),
            Map.entry("exception handling", List.of("exception handling", "exceptions")),
            Map.entry("sql", List.of("sql", "mysql", "postgresql")), Map.entry("git", List.of("git", "github")),
            Map.entry("spring boot", List.of("spring boot", "springboot")), Map.entry("maven", List.of("maven")),
            Map.entry("junit", List.of("junit", "unit testing")), Map.entry("rest api", List.of("rest api", "restful")),
            Map.entry("python", List.of("python")), Map.entry("excel", List.of("excel")),
            Map.entry("statistics", List.of("statistics", "statistical")),
            Map.entry("data visualization", List.of("data visualization", "visualisation")),
            Map.entry("power bi", List.of("power bi", "powerbi")), Map.entry("tableau", List.of("tableau")),
            Map.entry("pandas", List.of("pandas")), Map.entry("machine learning", List.of("machine learning", "ml")),
            Map.entry("data preprocessing", List.of("data preprocessing", "data cleaning")),
            Map.entry("scikit learn", List.of("scikit learn", "sklearn")),
            Map.entry("deep learning", List.of("deep learning")));

    public Set<String> extract(String text, Set<String> vocabulary) {
        String source = " " + text.toLowerCase(Locale.ROOT).replace('-', ' ') + " ";
        Set<String> found = new TreeSet<>();
        for (String skill : vocabulary) {
            for (String alias : ALIASES.getOrDefault(skill, List.of(skill))) {
                if (source.contains(" " + alias + " ") || source.contains(alias)) { found.add(skill); break; }
            }
        }
        return found;
    }
}
