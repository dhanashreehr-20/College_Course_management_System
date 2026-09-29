package com.college.service;

/**
 * Requirement A.20 - final class: this class only ever holds shared constants,
 * so it is marked final to make clear it must never be subclassed or extended -
 * there is no meaningful "specialised version" of a constants holder.
 * Requirement A.2 - demonstrates 'final' constants used across the project.
 */
public final class CollegeConstants {

    // A private constructor further blocks accidental instantiation.
    private CollegeConstants() {
    }

    public static final String COLLEGE_NAME = "Reva University";
    public static final double BASE_FEE = 5000.0;      // flat base fee per semester
    public static final double FEE_PER_CREDIT = 1200.0; // additional fee per enrolled credit
    public static final int MAX_COURSES_PER_STUDENT = 5;
}
