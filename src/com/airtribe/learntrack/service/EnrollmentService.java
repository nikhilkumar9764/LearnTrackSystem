package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class for Enrollment business logic.
 * Handles operations like enrolling students, retrieving enrollments, and updating status.
 */
public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;
    private StudentService studentService;
    private CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, 
                            StudentService studentService, 
                            CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Enrolls a student in a course.
     * @param studentId the student ID
     * @param courseId the course ID
     * @return the created enrollment
     * @throws EntityNotFoundException if student or course not found
     */
    public Enrollment enrollStudent(int studentId, int courseId) throws EntityNotFoundException {
        // Validate that student exists
        studentService.findStudentById(studentId);
        
        // Validate that course exists
        courseService.findCourseById(courseId);
        
        int id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, studentId, courseId, LocalDate.now(), EnrollmentStatus.ACTIVE);
        enrollmentRepository.addEnrollment(enrollment);
        return enrollment;
    }

    /**
     * Retrieves all enrollments for a student.
     * @param studentId the student ID
     * @return list of enrollments for the student
     * @throws EntityNotFoundException if student not found
     */
    public List<Enrollment> getEnrollmentsByStudent(int studentId) throws EntityNotFoundException {
        // Validate that student exists
        studentService.findStudentById(studentId);
        return enrollmentRepository.findByStudentId(studentId);
    }

    /**
     * Marks an enrollment as completed.
     * @param enrollmentId the enrollment ID
     * @throws EntityNotFoundException if enrollment not found
     */
    public void markEnrollmentCompleted(int enrollmentId) throws EntityNotFoundException {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment", enrollmentId);
        }
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
        enrollmentRepository.updateEnrollment(enrollment);
    }

    /**
     * Marks an enrollment as cancelled.
     * @param enrollmentId the enrollment ID
     * @throws EntityNotFoundException if enrollment not found
     */
    public void markEnrollmentCancelled(int enrollmentId) throws EntityNotFoundException {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment", enrollmentId);
        }
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.updateEnrollment(enrollment);
    }

    /**
     * Retrieves all enrollments.
     * @return list of all enrollments
     */
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.getAllEnrollments();
    }
}

