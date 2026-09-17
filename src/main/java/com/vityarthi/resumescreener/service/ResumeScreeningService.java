package com.vityarthi.resumescreener.service;

import com.vityarthi.resumescreener.model.*;
import com.vityarthi.resumescreener.nlp.*;
import java.util.*;

public class ResumeScreeningService {
    private final SkillExtractor skillExtractor = new SkillExtractor();
    private final TextProcessor textProcessor = new TextProcessor();

    public ScreeningResult analyze(String resume, JobProfile profile) {
        Set<String> vocabulary = new LinkedHashSet<>(profile.requiredSkills()); vocabulary.addAll(profile.preferredSkills());
        Set<String> found = skillExtractor.extract(resume, vocabulary);
        Set<String> missing = new LinkedHashSet<>(profile.requiredSkills()); missing.removeAll(found);
        long reqCount = profile.requiredSkills().stream().filter(found::contains).count();
        long prefCount = profile.preferredSkills().stream().filter(found::contains).count();
        double required = 100.0 * reqCount / Math.max(1, profile.requiredSkills().size());
        double preferred = 100.0 * prefCount / Math.max(1, profile.preferredSkills().size());
        String targetText = String.join(" ", vocabulary) + " " + profile.description();
        double cosine = 100.0 * textProcessor.cosineSimilarity(resume, targetText);
        double score = 0.60 * required + 0.25 * preferred + 0.15 * cosine;
        String category = score >= 75 ? "STRONG MATCH" : score >= 50 ? "DEVELOPING MATCH" : "NEEDS IMPROVEMENT";
        List<String> advice = new ArrayList<>();
        if (!missing.isEmpty()) advice.add("Prioritise required skills: " + String.join(", ", missing) + ".");
        if (preferred < 50) advice.add("Add one role-relevant project using a preferred technology.");
        if (cosine < 45) advice.add("Rewrite project bullets with concrete role keywords and outcomes.");
        if (advice.isEmpty()) advice.add("Quantify achievements and prepare examples for technical interviews.");
        return new ScreeningResult(round(score), round(required), round(preferred), round(cosine), category,
                Collections.unmodifiableSet(found), Collections.unmodifiableSet(missing), List.copyOf(advice));
    }
    private double round(double value) { return Math.round(value * 10.0) / 10.0; }
}
