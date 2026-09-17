package com.vityarthi.resumescreener.model;

import java.util.Set;

public record JobProfile(String role, Set<String> requiredSkills,
                         Set<String> preferredSkills, String description) { }
