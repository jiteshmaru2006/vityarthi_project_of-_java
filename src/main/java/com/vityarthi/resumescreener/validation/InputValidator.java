package com.vityarthi.resumescreener.validation;

import com.vityarthi.resumescreener.exception.ValidationException;

public final class InputValidator {
    private InputValidator() { }

    public static void validate(String candidate, String resume, String role) {
        if (candidate == null || candidate.isBlank()) throw new ValidationException("Candidate name is required.");
        if (resume == null || resume.isBlank()) throw new ValidationException("Resume text is required.");
        if (resume.length() < 40) throw new ValidationException("Resume text is too short for a useful comparison.");
        if (role == null || role.isBlank()) throw new ValidationException("Target role is required.");
    }
}
