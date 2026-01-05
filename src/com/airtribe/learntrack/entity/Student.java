package com.airtribe.learntrack.entity;

/**
 * Student class extending Person.
 * Demonstrates inheritance and method overriding.
 */
public class Student extends Person {
    private String batch;
    private boolean active;

    // Default constructor
    public Student() {
        super();
    }

    // Parameterized constructor with all fields
    public Student(int id, String firstName, String lastName, String email, String batch, boolean active) {
        super(id, firstName, lastName, email);
        this.batch = batch;
        this.active = active;
    }

    // Constructor without email (demonstrates overloading)
    public Student(int id, String firstName, String lastName, String batch, boolean active) {
        super(id, firstName, lastName);
        this.batch = batch;
        this.active = active;
    }

    // Constructor with minimal fields
    public Student(int id, String firstName, String lastName, String batch) {
        super(id, firstName, lastName);
        this.batch = batch;
        this.active = true; // Default to active
    }

    // Getters and Setters
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Method overriding - specialized behavior for Student
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", batch='" + batch + '\'' +
                ", active=" + active +
                '}';
    }
}

