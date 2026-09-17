package com.vityarthi.resumescreener.service;

import com.vityarthi.resumescreener.model.ScreeningResult;
import java.io.*;
import java.nio.file.*;

public class ReportService {
    public Path write(Path folder, String candidate, String role, ScreeningResult r) throws IOException {
        Files.createDirectories(folder);
        Path file = folder.resolve(candidate.toLowerCase().replaceAll("[^a-z0-9]+", "_") + "_resume_report.txt");
        String text = "AI BASED RESUME SCREENING REPORT\n\nCandidate: " + candidate + "\nTarget role: " + role
                + "\nOverall score: " + r.overallScore() + "%\nCategory: " + r.category()
                + "\nRequired skill coverage: " + r.requiredCoverage() + "%\nPreferred skill coverage: " + r.preferredCoverage()
                + "%\nText similarity: " + r.cosineSimilarity() + "%\nMatched skills: " + String.join(", ", r.matchedSkills())
                + "\nMissing required skills: " + (r.missingSkills().isEmpty() ? "None" : String.join(", ", r.missingSkills()))
                + "\n\nRecommendations:\n- " + String.join("\n- ", r.recommendations())
                + "\n\nEducational self-assessment only; not an automated hiring decision.\n";
        Files.writeString(file, text); return file;
    }
}
