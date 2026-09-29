package com.college.app;

import com.college.model.Course;
import com.college.model.Department;
import com.college.model.Faculty;
import com.college.model.Person;
import com.college.model.Student;
import com.college.service.CollegeConstants;
import com.college.service.CollegeService;

import java.util.Scanner;

/**
 * College Course Management System
 * Requirement A.21 - third package (com.college.app), keeps the console/menu
 * concerns separate from the model and service packages.
 * Requirement A.8  - Scanner-based console I/O with formatted output.
 * Requirement A.6  - all control-flow + jump statements are exercised from here.
 */
public class Main {

    // static field: shared across the whole run of the program (class/static scope)
    private static int menuVisits = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CollegeService service = new CollegeService();

        System.out.println("=====================================================");
        System.out.println("   Welcome to " + CollegeConstants.COLLEGE_NAME);
        System.out.println("   College Course Management System");
        System.out.println("=====================================================");

        seedSampleData(service); // pre-load a few records so the demo has data to show

        boolean running = true;
        // Requirement A.6 - do-while loop drives the whole menu
        do {
            menuVisits++; // static field mutated every iteration
            printMenu();

            int choice = readInt(sc, "Enter your choice: ");

            // Requirement A.6 - switch statement (with break on every case)
            switch (choice) {
                case 1:
                    addStudent(sc, service);
                    break;
                case 2:
                    addFaculty(sc, service);
                    break;
                case 3:
                    addCourse(sc, service);
                    break;
                case 4:
                    enrollStudent(sc, service);
                    break;
                case 5:
                    System.out.println("\n-- Student List --");
                    service.listStudents();
                    break;
                case 6:
                    System.out.println("\n-- Faculty List --");
                    service.listFaculty();
                    break;
                case 7:
                    System.out.println("\n-- Course Catalog --");
                    service.listCourses();
                    break;
                case 8:
                    searchStudent(sc, service);
                    break;
                case 9:
                    System.out.println("\n-- Polymorphism demo (Person reference, dynamic binding) --");
                    service.demonstratePolymorphism();
                    break;
                case 10:
                    System.out.println("\n-- Payable summary (interface reference) --");
                    double total = service.totalPayables();
                    System.out.printf("TOTAL across all Payables: %.2f%n", total);
                    break;
                case 11:
                    showObjectCounters();
                    break;
                case 0:
                    running = false; // local flag flips, loop condition re-checked
                    System.out.println("Thank you for using the College Course Management System. Goodbye!");
                    continue; // demonstrates 'continue' (skips the trailing message below, loop then exits)
                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
            System.out.println(); // spacing between menu rounds
        } while (running);

        System.out.println("Total menu rounds this session: " + menuVisits);
        sc.close();
    }

    private static void printMenu() {
        System.out.println("------------------- MENU -------------------");
        System.out.println(" 1. Add Student");
        System.out.println(" 2. Add Faculty");
        System.out.println(" 3. Add Course to catalog");
        System.out.println(" 4. Enroll Student in a Course");
        System.out.println(" 5. List Students");
        System.out.println(" 6. List Faculty");
        System.out.println(" 7. List Course Catalog");
        System.out.println(" 8. Search Student by name");
        System.out.println(" 9. Demonstrate polymorphism");
        System.out.println("10. Show total payables (fees + salaries)");
        System.out.println("11. Show object counters (static fields)");
        System.out.println(" 0. Exit");
        System.out.println("----------------------------------------------");
    }

    private static void addStudent(Scanner sc, CollegeService service) {
        System.out.print("Student ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        int age = readInt(sc, "Age: ");
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        Department dept = readDepartment(sc);

        Student s = new Student(id, name, age, email, dept);
        service.addStudent(s);
        System.out.println("Student added -> " + s);
    }

    private static void addFaculty(Scanner sc, CollegeService service) {
        System.out.print("Faculty ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        int age = readInt(sc, "Age: ");
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        Department dept = readDepartment(sc);
        double salary = readDouble(sc, "Basic salary: ");
        System.out.print("Designation (Professor/Associate Professor/Assistant Professor): ");
        String designation = sc.nextLine().trim();

        Faculty f = new Faculty(id, name, age, email, dept, salary, designation);
        service.addFaculty(f);
        System.out.println("Faculty added -> " + f);
    }

    private static void addCourse(Scanner sc, CollegeService service) {
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        System.out.print("Course name: ");
        String name = sc.nextLine().trim();
        int credits = readInt(sc, "Credits: ");

        Course c = new Course(code, name, credits);
        service.addCourse(c);
        System.out.println("Course added -> " + c);
    }

    private static void enrollStudent(Scanner sc, CollegeService service) {
        System.out.print("Enter Student ID to enroll: ");
        String id = sc.nextLine().trim();

        Student target = null;
        // Requirement A.6 - loop with 'break' as soon as the student is located
        for (Student s : service.getStudents()) {
            if (s.getId().equalsIgnoreCase(id)) {
                target = s;
                break;
            }
        }

        if (target == null) {
            System.out.println("No student found with ID " + id);
            return; // jump statement
        }

        System.out.println("Available courses:");
        service.listCourses();
        System.out.print("Enter course code to enroll in: ");
        String code = sc.nextLine().trim();

        Course chosen = null;
        for (Course c : service.getCourseCatalog()) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                chosen = c;
                break;
            }
        }

        if (chosen == null) {
            System.out.println("No course found with code " + code);
            return;
        }

        // Requirement A.10 - calling the Course-object overload of enrollCourse()
        boolean ok = target.enrollCourse(chosen);
        if (ok) {
            double fee = target.calculateFee(5.0); // 5% demo discount, exercises overloaded calculateFee
            System.out.printf("Enrolled! Updated fee balance for %s: %.2f%n", target.getName(), fee);
        } else {
            System.out.println("Could not enroll: course list is full for this student.");
        }
    }

    private static void searchStudent(Scanner sc, CollegeService service) {
        System.out.print("Enter (part of) the student's name: ");
        String query = sc.nextLine();
        Student result = service.searchStudentByName(query);

        // Requirement A.6 - simple if-else
        if (result != null) {
            System.out.println("Found: " + result);
        } else {
            System.out.println("No matching student found.");
        }
    }

    private static void showObjectCounters() {
        System.out.println("\n-- Static counters --");
        System.out.println("Total Students created : " + Student.getTotalStudents());
        System.out.println("Total Faculty created   : " + Faculty.getTotalFaculty());
        System.out.println("Total Courses created    : " + Course.getTotalCourses());
        System.out.println("Total Person objects ever: " + Person.getTotalPersonsCreated());
    }

    private static Department readDepartment(Scanner sc) {
        System.out.println("Department options: BioTech, CSE, ECE, CE, IT");
        System.out.print("Department: ");
        String input = sc.nextLine().trim().toUpperCase();
        Department dept;
        try {
            dept = Department.valueOf(input);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unrecognised department, defaulting to BioTech.");
            dept = Department.BioTech;
        }
        return dept;
    }

    private static int readInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a whole number: ");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine(); // consume the trailing newline
        return value;
    }

    private static double readDouble(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a numeric value: ");
            sc.next();
        }
        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }

    /**
     * Pre-loads a handful of records purely so a fresh run has something to
     * list/search/enroll against immediately. Uses several overloaded
     * constructors and both overloads of Student.enrollCourse().
     */
    private static void seedSampleData(CollegeService service) {
        Student s1 = new Student("S001", "Dhanashree H R", 20, "dhanashreehr@reva.edu.in", Department.BioTech);
        Student s2 = new Student("S002", "Adithi Rao", 21, "adithirao@reva.edu.in", Department.ECE);
        Student s3 = new Student(); // default constructor -> demonstrates constructor chaining via this()
        service.addStudent(s1);
        service.addStudent(s2);
        service.addStudent(s3);

        Faculty f1 = new Faculty("F001", "Dr. Meera Iyer", 45, "meeraiyer@reva.edu.in", Department.BioTech, 60000.0, "Professor");
        Faculty f2 = new Faculty(); // default constructor
        service.addFaculty(f1);
        service.addFaculty(f2);

        Course c1 = new Course("CS101", "Bioinformatics", 4);
        Course c2 = new Course("CS102", "Object Oriented Programming", 3);
        Course c3 = new Course("CS103", "Database Systems", 3);
        service.addCourse(c1);
        service.addCourse(c2);
        service.addCourse(c3);

        s1.enrollCourse(c1);                       // overload #1: takes a Course object
        s1.enrollCourse("CS102", "Object Oriented Programming", 3); // overload #2: takes raw details
        s2.enrollCourse(c1);
        s1.calculateFee(10.0); // 10% seed discount so fee balances aren't zero when first listed
        s2.calculateFee();
    }
}
