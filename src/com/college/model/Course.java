package com.college.model;

/**
 * Requirement A.1 - Third/fourth standalone class demonstrating encapsulation
 * (private fields + public getters/setters). Not part of the Person hierarchy -
 * a Course is something a Student *has*, not *is*.
 */
public class Course {
    private String courseCode;
    private String courseName;
    private int credits;

    private static int totalCourses = 0; // Requirement A.11 - static field/method

    // Requirement A.9 - default constructor
    public Course() {
        this("C000", "Unnamed Course", 3);
    }

    // Requirement A.9 - parameterized constructor (this() chaining shown in Student's overload too)
    public Course(String courseCode, String courseName, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        totalCourses++;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public static int getTotalCourses() {
        return totalCourses;
    }

    // Requirement A.19 - toString() override
    @Override
    public String toString() {
        return String.format("%s - %s (%d credits)", courseCode, courseName, credits);
    }

    // Requirement A.19 - equals() override, courses are identified by their code
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        // Requirement A.13 - String method equalsIgnoreCase used for comparison
        return this.courseCode.equalsIgnoreCase(other.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.toLowerCase().hashCode();
    }
}
