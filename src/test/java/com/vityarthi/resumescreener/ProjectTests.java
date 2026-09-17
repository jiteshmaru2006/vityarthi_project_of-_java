package com.vityarthi.resumescreener;

import com.vityarthi.resumescreener.exception.ValidationException;
import com.vityarthi.resumescreener.model.*;
import com.vityarthi.resumescreener.service.ResumeScreeningService;
import com.vityarthi.resumescreener.validation.InputValidator;
import java.util.Set;

public class ProjectTests {
    public static void main(String[] args) {
        JobProfile javaRole = new JobProfile("Java Developer",
                Set.of("java","oop","collections","exception handling","sql","git"),
                Set.of("spring boot","maven","junit","rest api"),
                "backend development object oriented programming database debugging clean code");
        ScreeningResult strong = new ResumeScreeningService().analyze(
                "Java object oriented programming collections exception handling SQL Git Spring Boot Maven JUnit REST API backend database debugging", javaRole);
        check(strong.overallScore() >= 75, "strong resume should be a strong match");
        ScreeningResult weak = new ResumeScreeningService().analyze("Java student with one small project and basic programming", javaRole);
        check(weak.missingSkills().contains("sql"), "weak resume should identify SQL gap");
        try { InputValidator.validate("", "long enough resume text for validation testing", "Java Developer");
            throw new AssertionError("blank name should fail");
        } catch (ValidationException expected) { }
        System.out.println("All 3 project tests passed.");
    }
    private static void check(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
