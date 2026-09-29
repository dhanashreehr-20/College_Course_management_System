package com.college.model;

/**
 * Requirement A.14 - Base class of the inheritance hierarchy (Person -> Student, Faculty)
 * Requirement A.17 - Abstract class with an abstract method (displayDetails)
 * Requirement A.1  - Encapsulation: private fields with public getters/setters
 */
public abstract class Person {
    // ---- instance fields (Requirement A.2 - instance scope) ----
    private final String id;
    private String name;
    private int age;
    private String email;

    // static field shared by every Person that has ever been created
    private static int totalPersonsCreated = 0;

    // Requirement A.9 - default (no-arg) constructor
    public Person() {
        this("UNKNOWN", "Unnamed", 0, "unknown@reva.edu");
    }

    // Requirement A.9 - parameterized constructor
    // Requirement A.12 - explicit 'this' used to resolve field/parameter shadowing
    public Person(String id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        totalPersonsCreated++; // static field updated by every constructor call
    }

    // ---- getters / setters (encapsulation) ----
    // Requirement A.20 - final method: the ID accessor must behave identically for
    // every subclass (an ID must never be re-interpreted), so it is marked final
    // to prevent Student/Faculty from overriding it.
    public final String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Requirement A.12 - 'this' resolves shadowing between the field 'name' and parameter 'name'
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static int getTotalPersonsCreated() {
        return totalPersonsCreated;
    }

    // Requirement A.17 - abstract method every subclass MUST implement
    public abstract void displayDetails();

    // Requirement A.19 - toString() override at the base of the hierarchy;
    // subclasses call super.toString() and append their own details.
    @Override
    public String toString() {
        return String.format("[%s] %s (Age: %d, Email: %s)", id, name, age, email);
    }

    // Requirement A.19 - equals() override based on the unique id
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;
        Person other = (Person) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
