package com.vityarthi.resumescreener.model;

import java.util.List;
import java.util.Set;

public record ScreeningResult(double overallScore, double requiredCoverage,
                              double preferredCoverage, double cosineSimilarity,
                              String category, Set<String> matchedSkills,
                              Set<String> missingSkills, List<String> recommendations) { }
