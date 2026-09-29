package com.college.service;

import com.college.model.Course;
import com.college.model.Faculty;
import com.college.model.Payable;
import com.college.model.Person;
import com.college.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Requirement A.21 - lives in its own package (com.college.service), separate
 * from com.college.model, with correct import statements above.
 * Requirement A.7 - also demonstrates a "list of objects" (ArrayList<Student> etc.)
 * complementing the raw array of Course objects used inside Student.
 */
public class CollegeService {

    private final List<Student> students = new ArrayList<>();
    private final List<Faculty> facultyList = new ArrayList<>();
    private final List<Course> courseCatalog = new ArrayList<>();

    public void addStudent(Student s) {
        students.add(s);
    }

    public void addFaculty(Faculty f) {
        facultyList.add(f);
    }

    public void addCourse(Course c) {
        courseCatalog.add(c);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    public List<Course> getCourseCatalog() {
        return courseCatalog;
    }

    // Requirement A.6 - for-each loop + formatted console output (printf)
    public void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered yet.");
            return; // jump statement: return
        }
        System.out.printf("%-6s %-18s %-5s %-6s %-30s%n", "ID", "Name", "Age", "Dept", "Email");
        for (Student s : students) {
            System.out.printf("%-6s %-18s %-5d %-6s %-30s%n", s.getId(), s.getName(), s.getAge(), s.getDepartment(), s.getEmail());
        }
    }

    public void listFaculty() {
        if (facultyList.isEmpty()) {
            System.out.println("No faculty registered yet.");
            return;
        }
        System.out.printf("%-6s %-18s %-6s %-22s %-12s%n", "ID", "Name", "Dept", "Designation", "Salary");
        for (Faculty f : facultyList) {
            System.out.printf("%-6s %-18s %-6s %-22s %-12.2f%n", f.getId(), f.getName(), f.getDepartment(), f.getDesignation(), f.getBasicSalary());
        }
    }

    public void listCourses() {
        if (courseCatalog.isEmpty()) {
            System.out.println("No courses in the catalog yet.");
            return;
        }
        int index = 1;
        for (Course c : courseCatalog) {
            System.out.println(index + ". " + c);
            index++;
        }
    }

    /**
     * Requirement A.13 - uses several String class methods on user-supplied text:
     * trim(), split(), and contains() / equalsIgnoreCase() while searching.
     * Requirement A.6 - loop with 'break' used to stop as soon as a match is found.
     */
    public Student searchStudentByName(String rawQuery) {
        String query = rawQuery.trim(); // remove stray whitespace from console input
        Student found = null;
        for (Student s : students) {
            // split the stored name into tokens and compare each token case-insensitively
            String[] nameTokens = s.getName().trim().split("\\s+");
            for (String token : nameTokens) {
                if (token.equalsIgnoreCase(query) || s.getName().toLowerCase().contains(query.toLowerCase())) {
                    found = s;
                    break; // jump statement: break out of the inner loop
                }
            }
            if (found != null) {
                break; // and out of the outer loop too
            }
        }
        return found;
    }

    /**
     * Requirement A.16 - dynamic binding demonstration: a Person[] array holds a
     * mix of Student and Faculty objects, and calling displayDetails() on each
     * dispatches to the correct overridden method at runtime.
     */
    public void demonstratePolymorphism() {
        Person[] people = new Person[students.size() + facultyList.size()];
        int idx = 0;
        for (Student s : students) {
            people[idx++] = s; // upcasting: Student reference stored as Person
        }
        for (Faculty f : facultyList) {
            people[idx++] = f;
        }

        for (Person p : people) {
            p.displayDetails(); // dynamic (runtime) binding chooses Student's or Faculty's version
            System.out.println();
        }
    }

    /**
     * Requirement A.18 - accesses Student and Faculty purely through the Payable
     * interface reference type, proving the interface contract works uniformly
     * across unrelated branches of the hierarchy.
     */
    public double totalPayables() {
        List<Payable> payables = new ArrayList<>();
        payables.addAll(students);
        payables.addAll(facultyList);

        double total = 0.0;
        for (Payable p : payables) {
            System.out.printf("%-20s : %10.2f%n", p.paymentDescription(), p.calculatePayment());
            total += p.calculatePayment();
        }
        return total;
    }
}
