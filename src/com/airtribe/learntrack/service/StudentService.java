package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import java.util.List;

/**
 * Service class for Student business logic.
 * Handles operations like adding, retrieving, and managing students.
 */
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Adds a new student to the system.
     * @param firstName student's first name
     * @param lastName student's last name
     * @param email student's email
     * @param batch student's batch
     * @return the created student
     */
    public Student addStudent(String firstName, String lastName, String email, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch, true);
        studentRepository.addStudent(student);
        return student;
    }

    /**
     * Adds a new student without email (method overloading).
     * @param firstName student's first name
     * @param lastName student's last name
     * @param batch student's batch
     * @return the created student
     */
    public Student addStudent(String firstName, String lastName, String batch) {
        int id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, batch, true);
        studentRepository.addStudent(student);
        return student;
    }

    /**
     * Retrieves all students.
     * @return list of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    /**
     * Retrieves all active students.
     * @return list of active students
     */
    public List<Student> getActiveStudents() {
        return studentRepository.getActiveStudents();
    }

    /**
     * Finds a student by ID.
     * @param id the student ID
     * @return the student if found
     * @throws EntityNotFoundException if student not found
     */
    public Student findStudentById(int id) throws EntityNotFoundException {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("Student", id);
        }
        return student;
    }

    /**
     * Deactivates a student.
     * @param id the student ID
     * @throws EntityNotFoundException if student not found
     */
    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("Student", id);
        }
        studentRepository.deactivateStudent(id);
    }
}

