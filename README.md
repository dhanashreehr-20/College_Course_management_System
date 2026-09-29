# College Course Management System

A console-based Java application built for the REVA University Java Practical Assignment (Unit-I & Unit-II OOP concepts).

**Domain Assigned:** College Course Management System  
**Student Name:** Dhanashree H R  
**SRN:** R24SA011  

---

## How to Compile & Run

### Standard Command Line (Windows PowerShell / Bash)
```bash
# Navigate to the project root directory
cd R24SA011_Dhanashree_HR_JavaProject

# Compile all source files into the 'bin' output directory
javac -d out src/com/college/model/*.java src/com/college/service/*.java src/com/college/app/*.java

# Run the Main application
java -cp out com.college.app.Main
```

---

## Project Structure & Architecture

```
R24SA011_Dhanashree_HR_JavaProject/
├── src/
│   ├── com/college/model/
│   │   ├── Department.java        (Enum representing academic branches)
│   │   ├── Payable.java           (Interface for payment calculations)
│   │   ├── Person.java            (Abstract base class)
│   │   ├── Student.java           (Subclass representing students)
│   │   ├── Faculty.java           (Subclass representing faculty members)
│   │   └── Course.java            (Encapsulated course object)
│   ├── com/college/service/
│   │   ├── CollegeConstants.java  (Final utility class for constants)
│   │   └── CollegeService.java    (Business logic & collection management)
│   └── com/college/app/
│       └── Main.java              (Console driver with interactive menu)
├── README.md                      (Feature traceability & documentation)
└── sample_output.txt              (Captured console run)
```

---

## Traceability Matrix — Mandatory Requirements (A.1 to A.21)

| Requirement Code | Mandatory Requirement Description | Implementation Location |
| :--- | :--- | :--- |
| **A.1** | ≥3–4 Encapsulated Classes | `Person`, `Student`, `Faculty`, `Course` (private fields, public getters/setters) |
| **A.2** | Varied Data Types & Scopes | Types: `String`, `int`, `double`, `char`, `boolean`. Constants: `CollegeConstants.BASE_FEE`, `Student.MAX_COURSES` |
| **A.3** | Operator Precedence | Formula in `Student.calculateFee(double)` combining multiplication, division, addition, and subtraction |
| **A.4** | Type Conversion / Casting | Double-to-int fee truncation in `Student.calculateFee(double)` (`(int) totalFee`) |
| **A.5** | Enum | `Department.java` enum (`BioTech`, `CSE`, `ECE`, `CE`, `IT`) with custom field and constructor |
| **A.6** | Control Flow & Jump Statements | `if-else`, `switch`, `for`, `while`, `do-while`, `break`, `continue`, `return` across `Main.java`, `CollegeService.java`, and `Student.java` |
| **A.7** | Array of Objects | `Course[] enrolledCourses` array in `Student`; `List<Student>` / `List<Faculty>` in `CollegeService` |
| **A.8** | Console I/O & Formatting | `Scanner` input reading in `Main.java`; `System.out.printf` and `String.format` formatting |
| **A.9** | Constructor Overloading | Default and parameterized constructors in `Person`, `Student`, `Faculty`, `Course` |
| **A.10** | Method Overloading | Overloaded `enrollCourse()`, `calculateFee()`, and `calculateGrossSalary()` |
| **A.11** | Static Fields & Methods | Object counters (`totalPersonsCreated`, `totalStudents`, `totalFaculty`, `totalCourses`) |
| **A.12** | Explicit `this` Usage | Shadowing resolution (`this.name = name`) and constructor chaining (`this(...)`) |
| **A.13** | String Class Methods | `.trim()`, `.split()`, `.equalsIgnoreCase()`, `.contains()`, `.toLowerCase()` in input processing and search |
| **A.14** | Inheritance Hierarchy | Abstract base class `Person` extended by `Student` and `Faculty` |
| **A.15** | `super` Keyword | Parent constructor calls (`super(id, name, age, email)`) and method calls (`super.toString()`) |
| **A.16** | Overriding & Dynamic Binding | `Person.displayDetails()` overridden in `Student` and `Faculty`, dispatched via `Person[]` in `CollegeService` |
| **A.17** | Abstract Class & Method | `Person` abstract class containing `public abstract void displayDetails()` |
| **A.18** | Interface Usage | `Payable` interface (`calculatePayment()`, `paymentDescription()`) accessed via `List<Payable>` reference |
| **A.19** | `toString()` & `equals()` Overrides | Overridden across `Person`, `Student`, `Faculty`, `Course` |
| **A.20** | `final` Method / Class | `Person.getId()` final method; `CollegeConstants` final class (both with explanatory comments) |
| **A.21** | Multiple Custom Packages | Organized across `com.college.model`, `com.college.service`, and `com.college.app` |
