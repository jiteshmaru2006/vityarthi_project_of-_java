package com.vityarthi.resumescreener.repository;

import com.vityarthi.resumescreener.model.ScreeningResult;
import java.io.*;
import java.nio.file.*;
import java.time.Instant;

public class ScreeningHistoryRepository {
    public void append(Path file, String candidate, String role, ScreeningResult r) throws IOException {
        Files.createDirectories(file.getParent()); boolean header = Files.notExists(file);
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (header) w.write("timestamp,candidate,role,score,category,required_coverage,preferred_coverage,cosine_similarity\n");
            w.write(String.format("%s,%s,%s,%.1f,%s,%.1f,%.1f,%.1f%n", Instant.now(), clean(candidate), clean(role), r.overallScore(), r.category(), r.requiredCoverage(), r.preferredCoverage(), r.cosineSimilarity()));
        }
    }
    private String clean(String v) { return v.replace(',', ' '); }
}
