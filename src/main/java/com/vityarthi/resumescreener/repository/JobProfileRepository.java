package com.vityarthi.resumescreener.repository;

import com.vityarthi.resumescreener.model.JobProfile;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JobProfileRepository {
    public List<JobProfile> load(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path); List<JobProfile> profiles = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) { List<String> c = parseCsv(lines.get(i)); if (c.size() != 4) throw new IOException("Malformed profile row " + (i + 1)); profiles.add(new JobProfile(c.get(0), split(c.get(1)), split(c.get(2)), c.get(3))); }
        return profiles;
    }
    private Set<String> split(String value) { return new LinkedHashSet<>(Arrays.asList(value.split(";"))); }
    private List<String> parseCsv(String line) { List<String> cells = new ArrayList<>(); StringBuilder b = new StringBuilder(); boolean quoted = false; for (char ch : line.toCharArray()) { if (ch == '"') quoted = !quoted; else if (ch == ',' && !quoted) { cells.add(b.toString().trim()); b.setLength(0); } else b.append(ch); } cells.add(b.toString().trim()); return cells; }
}
