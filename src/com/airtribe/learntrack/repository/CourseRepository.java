package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Course data in memory.
 * Uses ArrayList for dynamic storage.
 */
public class CourseRepository {
    private List<Course> courses;

    public CourseRepository() {
        this.courses = new ArrayList<>();
    }

    /**
     * Adds a course to the repository.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        courses.add(course);
    }

    /**
     * Retrieves all courses.
     * @return list of all courses
     */
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses); // Return a copy to maintain encapsulation
    }

    /**
     * Retrieves all active courses.
     * @return list of active courses
     */
    public List<Course> getActiveCourses() {
        List<Course> activeCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.isActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }

    /**
     * Finds a course by ID.
     * @param id the course ID
     * @return the course if found, null otherwise
     */
    public Course findById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    /**
     * Updates a course's information.
     * @param updatedCourse the course with updated information
     * @return true if update was successful, false if course not found
     */
    public boolean updateCourse(Course updatedCourse) {
        Course existingCourse = findById(updatedCourse.getId());
        if (existingCourse != null) {
            int index = courses.indexOf(existingCourse);
            courses.set(index, updatedCourse);
            return true;
        }
        return false;
    }

    /**
     * Activates a course (sets active to true).
     * @param id the course ID
     * @return true if activation was successful, false if course not found
     */
    public boolean activateCourse(int id) {
        Course course = findById(id);
        if (course != null) {
            course.setActive(true);
            return true;
        }
        return false;
    }

    /**
     * Deactivates a course (sets active to false).
     * @param id the course ID
     * @return true if deactivation was successful, false if course not found
     */
    public boolean deactivateCourse(int id) {
        Course course = findById(id);
        if (course != null) {
            course.setActive(false);
            return true;
        }
        return false;
    }
}

