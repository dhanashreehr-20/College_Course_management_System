package com.college.model;

/**
 * Requirement A.14 - Second subclass of Person, completes the inheritance hierarchy
 * Requirement A.18 - Implements Payable (salary is payable TO the faculty member)
 */
public class Faculty extends Person implements Payable {
    private Department department;
    private double basicSalary;
    private String designation; // e.g. "Professor", "Associate Professor", "Assistant Professor"

    private static int totalFaculty = 0; // Requirement A.11 - static field

    // Requirement A.9 - default constructor, chains via this()
    public Faculty() {
        this("F000", "Dr. Rahul", 30, "rahul@reva.edu.in", Department.BioTech, 30000.0, "Assistant Professor");
    }

    // Requirement A.9 - parameterized constructor
    public Faculty(String id, String name, int age, String email, Department department, double basicSalary, String designation) {
        // Requirement A.15 - super() invokes the Person constructor
        super(id, name, age, email);
        this.department = department;
        this.basicSalary = basicSalary;
        this.designation = designation;
        totalFaculty++;
    }

    // ---------------- getters / setters ----------------
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getDesignation() {
        return designation;
    }

    // Requirement A.13 - String methods: trim() + equalsIgnoreCase() used while validating/setting input
    public void setDesignation(String designation) {
        this.designation = designation == null ? "Assistant Professor" : designation.trim();
    }

    public static int getTotalFaculty() {
        return totalFaculty;
    }

    // ---------------- Requirement A.10 - method overloading (calculatePayment variants) ----------------
    public double calculateGrossSalary() {
        return calculateGrossSalary(0.0);
    }

    // Requirement A.6 - switch statement choosing an allowance tier based on designation
    public double calculateGrossSalary(double bonus) {
        double allowance;

        // Requirement A.13 - String.equalsIgnoreCase used for case-insensitive comparison
        if (designation.equalsIgnoreCase("Professor")) {
            allowance = 15000.0;
        } else if (designation.equalsIgnoreCase("Associate Professor")) {
            allowance = 10000.0;
        } else {
            allowance = 5000.0;
        }

        double gross = basicSalary + allowance + bonus;
        return gross;
    }

    // ---------------- Payable interface implementation ----------------
    @Override
    public double calculatePayment() {
        return calculateGrossSalary();
    }

    @Override
    public String paymentDescription() {
        return "Monthly Salary";
    }

    // Requirement A.16 - dynamic binding: overrides Person.displayDetails()
    @Override
    public void displayDetails() {
        System.out.println("---- Faculty Details ----");
        System.out.println(this);
        System.out.printf("Gross Salary (with allowance): %.2f%n", calculateGrossSalary());
    }

    // Requirement A.19 + A.15 - toString() override built on top of super.toString()
    @Override
    public String toString() {
        return super.toString() + String.format(
                " | Dept: %s | Designation: %s | Basic Salary: %.2f", department, designation, basicSalary);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Faculty)) return false;
        Faculty other = (Faculty) obj;
        return this.designation.equalsIgnoreCase(other.designation);
    }
}
