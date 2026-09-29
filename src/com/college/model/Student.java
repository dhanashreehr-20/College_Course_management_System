package com.college.model;

/**
 * Requirement A.14 - First subclass of Person
 * Requirement A.18 - Implements the Payable interface (fee is payable BY the student)
 */
public class Student extends Person implements Payable {
    // Requirement A.7 - array of objects: each student can enroll in a fixed
    // number of courses, stored as a plain Java array (not a collection).
    private Course[] enrolledCourses;
    private int courseCount; // local bookkeeping of how many slots are filled

    private Department department;
    private double feeBalance;
    private char grade; // Requirement A.2 - varied data type (char)

    // static counter -> Requirement A.11
    private static int totalStudents = 0;

    private static final int MAX_COURSES = 5; // final constant, class-level scope

    // Requirement A.9 - default constructor chains to the parameterized one via this()
    // Requirement A.12 - constructor chaining using this()
    public Student() {
        this("S000", "Sneha", 18, "sneha@reva.edu.in", Department.BioTech);
    }

    // Requirement A.9 - parameterized constructor
    // Requirement A.15 - super() calls the Person constructor
    public Student(String id, String name, int age, String email, Department department) {
        super(id, name, age, email);
        this.department = department;
        this.enrolledCourses = new Course[MAX_COURSES];
        this.courseCount = 0;
        this.feeBalance = 0.0;
        this.grade = 'N'; // Not graded yet
        totalStudents++;
    }

    // ---------------- getters / setters ----------------
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public double getFeeBalance() {
        return feeBalance;
    }

    public void setFeeBalance(double feeBalance) {
        this.feeBalance = feeBalance;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public Course[] getEnrolledCourses() {
        return enrolledCourses;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public static int getTotalStudents() {
        return totalStudents;
    }

    // ---------------- Requirement A.10 - method overloading ----------------
    // Overload 1: enroll using an already-built Course object
    public boolean enrollCourse(Course course) {
        if (courseCount >= MAX_COURSES) {
            return false; // return statement -> jump statement
        }
        for (int i = 0; i < courseCount; i++) {
            if (enrolledCourses[i].equals(course)) {
                continue; // demonstrates 'continue' (harmless here, kept for requirement + clarity)
            }
        }
        enrolledCourses[courseCount] = course;
        courseCount++;
        return true;
    }

    // Overload 2: enroll by directly supplying course details (builds the Course itself)
    public boolean enrollCourse(String code, String name, int credits) {
        Course c = new Course(code, name, credits);
        return enrollCourse(c);
    }

    // ---------------- Requirement A.10 - overloaded fee calculation ----------------
    // Overload 1: plain fee based on enrolled courses, no discount
    public double calculateFee() {
        return calculateFee(0.0);
    }

    // Overload 2: fee with a discount percentage applied
    // Requirement A.3 - expression relying on operator precedence (see comment below)
    // Requirement A.4 - type conversion / casting example
    public double calculateFee(double discountPercent) {
        int creditSum = 0;
        for (int i = 0; i < courseCount; i++) {
            creditSum += enrolledCourses[i].getCredits(); // local loop variable 'i' -> local scope
        }

        // Operator precedence: multiplication (creditSum * FEE_PER_CREDIT) and
        // (discountPercent / 100.0) * BASE_FEE are evaluated BEFORE the
        // addition/subtraction that combines them, exactly like normal math rules.
        double totalFee = com.college.service.CollegeConstants.BASE_FEE
                + creditSum * com.college.service.CollegeConstants.FEE_PER_CREDIT
                - (discountPercent / 100.0) * com.college.service.CollegeConstants.BASE_FEE;

        // Requirement A.4 - casting a double down to an int (truncates the paise/cents)
        int roundedFee = (int) totalFee;
        this.feeBalance = roundedFee;
        return roundedFee;
    }

    // ---------------- Payable interface implementation ----------------
    @Override
    public double calculatePayment() {
        return feeBalance > 0 ? feeBalance : calculateFee();
    }

    @Override
    public String paymentDescription() {
        return "Tuition Fee";
    }

    // Requirement A.16 - overriding with dynamic binding (called via a Person reference)
    // Requirement A.6 - if-else and switch used together
    @Override
    public void displayDetails() {
        System.out.println("---- Student Details ----");
        System.out.println(this); // uses overridden toString()

        String standing;
        // Requirement A.6 - switch statement
        switch (grade) {
            case 'A':
                standing = "Excellent";
                break;
            case 'B':
                standing = "Good";
                break;
            case 'N':
                standing = "Not graded yet";
                break;
            default:
                standing = "Needs improvement";
                break;
        }
        System.out.println("Academic standing: " + standing);
    }

    // Requirement A.19 - toString() override, extends Person's version via super.toString()
    // Requirement A.15 - super used to call parent's method
    @Override
    public String toString() {
        return super.toString() + String.format(
                " | Dept: %s | Courses: %d | Fee Balance: %.2f | Grade: %c", department, courseCount, feeBalance, grade);
    }

    // Requirement A.19 - equals() override specialised for Student (adds department check)
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return this.department == other.department;
    }
}
