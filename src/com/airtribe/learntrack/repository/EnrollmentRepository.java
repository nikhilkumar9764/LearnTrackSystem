package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Enrollment data in memory.
 * Uses ArrayList for dynamic storage.
 */
public class EnrollmentRepository {
    private List<Enrollment> enrollments;

    public EnrollmentRepository() {
        this.enrollments = new ArrayList<>();
    }

    /**
     * Adds an enrollment to the repository.
     * @param enrollment the enrollment to add
     */
    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    /**
     * Retrieves all enrollments.
     * @return list of all enrollments
     */
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments); // Return a copy to maintain encapsulation
    }

    /**
     * Finds enrollments by student ID.
     * @param studentId the student ID
     * @return list of enrollments for the student
     */
    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> studentEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                studentEnrollments.add(enrollment);
            }
        }
        return studentEnrollments;
    }

    /**
     * Finds enrollments by course ID.
     * @param courseId the course ID
     * @return list of enrollments for the course
     */
    public List<Enrollment> findByCourseId(int courseId) {
        List<Enrollment> courseEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == courseId) {
                courseEnrollments.add(enrollment);
            }
        }
        return courseEnrollments;
    }

    /**
     * Finds an enrollment by ID.
     * @param id the enrollment ID
     * @return the enrollment if found, null otherwise
     */
    public Enrollment findById(int id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        return null;
    }

    /**
     * Updates an enrollment's information.
     * @param updatedEnrollment the enrollment with updated information
     * @return true if update was successful, false if enrollment not found
     */
    public boolean updateEnrollment(Enrollment updatedEnrollment) {
        Enrollment existingEnrollment = findById(updatedEnrollment.getId());
        if (existingEnrollment != null) {
            int index = enrollments.indexOf(existingEnrollment);
            enrollments.set(index, updatedEnrollment);
            return true;
        }
        return false;
    }
}

