package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import java.util.List;

/**
 * Service class for Course business logic.
 * Handles operations like adding, retrieving, and managing courses.
 */
public class CourseService {
    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Adds a new course to the system.
     * @param courseName name of the course
     * @param description course description
     * @param durationInWeeks duration in weeks
     * @return the created course
     */
    public Course addCourse(String courseName, String description, int durationInWeeks) {
        int id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, durationInWeeks, true);
        courseRepository.addCourse(course);
        return course;
    }

    /**
     * Retrieves all courses.
     * @return list of all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    /**
     * Retrieves all active courses.
     * @return list of active courses
     */
    public List<Course> getActiveCourses() {
        return courseRepository.getActiveCourses();
    }

    /**
     * Finds a course by ID.
     * @param id the course ID
     * @return the course if found
     * @throws EntityNotFoundException if course not found
     */
    public Course findCourseById(int id) throws EntityNotFoundException {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course", id);
        }
        return course;
    }

    /**
     * Activates a course.
     * @param id the course ID
     * @throws EntityNotFoundException if course not found
     */
    public void activateCourse(int id) throws EntityNotFoundException {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course", id);
        }
        courseRepository.activateCourse(id);
    }

    /**
     * Deactivates a course.
     * @param id the course ID
     * @throws EntityNotFoundException if course not found
     */
    public void deactivateCourse(int id) throws EntityNotFoundException {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course", id);
        }
        courseRepository.deactivateCourse(id);
    }
}

