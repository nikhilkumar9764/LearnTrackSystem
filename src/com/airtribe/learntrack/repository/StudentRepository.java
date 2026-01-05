package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Student data in memory.
 * Uses ArrayList for dynamic storage.
 */
public class StudentRepository {
    private List<Student> students;

    public StudentRepository() {
        this.students = new ArrayList<>();
    }

    /**
     * Adds a student to the repository.
     * @param student the student to add
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Retrieves all students.
     * @return list of all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to maintain encapsulation
    }

    /**
     * Retrieves all active students.
     * @return list of active students
     */
    public List<Student> getActiveStudents() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }

    /**
     * Finds a student by ID.
     * @param id the student ID
     * @return the student if found, null otherwise
     */
    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    /**
     * Updates a student's information.
     * @param updatedStudent the student with updated information
     * @return true if update was successful, false if student not found
     */
    public boolean updateStudent(Student updatedStudent) {
        Student existingStudent = findById(updatedStudent.getId());
        if (existingStudent != null) {
            int index = students.indexOf(existingStudent);
            students.set(index, updatedStudent);
            return true;
        }
        return false;
    }

    /**
     * Deactivates a student (sets active to false).
     * @param id the student ID
     * @return true if deactivation was successful, false if student not found
     */
    public boolean deactivateStudent(int id) {
        Student student = findById(id);
        if (student != null) {
            student.setActive(false);
            return true;
        }
        return false;
    }
}

