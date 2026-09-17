package com.vityarthi.resumescreener.app;

import com.vityarthi.resumescreener.exception.ValidationException;
import com.vityarthi.resumescreener.model.*;
import com.vityarthi.resumescreener.repository.*;
import com.vityarthi.resumescreener.service.*;
import com.vityarthi.resumescreener.validation.InputValidator;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ConsoleApplication {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("============================================================");
            System.out.println(" AI-BASED RESUME SCREENING AND SKILL GAP ANALYZER");
            System.out.println("============================================================");
            List<JobProfile> profiles = new JobProfileRepository().load(Path.of("data/job_profiles.csv"));
            System.out.print("Candidate name: "); String candidate = scanner.nextLine().trim();
            System.out.println("Available roles:");
            for (int i = 0; i < profiles.size(); i++) System.out.println("  " + (i + 1) + ". " + profiles.get(i).role());
            System.out.print("Choose role (1-" + profiles.size() + "): ");
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 1 || choice > profiles.size()) throw new ValidationException("Role choice is outside the listed range.");
            JobProfile profile = profiles.get(choice - 1);
            System.out.print("Resume file [samples/jitesh_resume.txt]: "); String entry = scanner.nextLine().trim();
            Path resumeFile = Path.of(entry.isBlank() ? "samples/jitesh_resume.txt" : entry);
            String resume = Files.readString(resumeFile);
            InputValidator.validate(candidate, resume, profile.role());
            ScreeningResult result = new ResumeScreeningService().analyze(resume, profile);
            Path report = new ReportService().write(Path.of("reports"), candidate, profile.role(), result);
            new ScreeningHistoryRepository().append(Path.of("data/screening_history.csv"), candidate, profile.role(), result);
            System.out.println("\n---------------------- ANALYSIS RESULT ----------------------");
            System.out.printf("Target role              : %s%n", profile.role());
            System.out.printf("Overall match score      : %.1f%%%n", result.overallScore());
            System.out.printf("Match category           : %s%n", result.category());
            System.out.printf("Required skill coverage  : %.1f%%%n", result.requiredCoverage());
            System.out.printf("Preferred skill coverage : %.1f%%%n", result.preferredCoverage());
            System.out.printf("Text similarity          : %.1f%%%n", result.cosineSimilarity());
            System.out.println("Matched skills           : " + String.join(", ", result.matchedSkills()));
            System.out.println("Missing required skills  : " + (result.missingSkills().isEmpty() ? "None" : String.join(", ", result.missingSkills())));
            System.out.println("Recommendations:"); result.recommendations().forEach(x -> System.out.println("  - " + x));
            System.out.println("Report saved             : " + report);
            System.out.println("History updated          : data/screening_history.csv");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Educational self-assessment only - not a hiring decision.");
            System.out.println("Program completed successfully.");
        } catch (ValidationException | NumberFormatException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }
}
