package com.college.model;

/**
 * Requirement A.5 - Enum
 * Represents the academic departments available in the college.
 * Used by both Student and Faculty classes.
 */
public enum Department {
    BioTech("Biotechnology"),
    CSE("Computer Science & Engineering"),
    ECE("Electronics & Communication Engineering"),
    CE("Civil Engineering"),
    IT("Information Technology");

    // instance field of the enum (each constant carries its own copy)
    private final String fullName;

    Department(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
