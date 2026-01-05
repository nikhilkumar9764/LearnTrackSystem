package com.airtribe.learntrack.constants;

/**
 * Constants for menu options.
 * Using static final fields to represent menu choices.
 */
public class MenuOptions {
    // Main menu options
    public static final int STUDENT_MANAGEMENT = 1;
    public static final int COURSE_MANAGEMENT = 2;
    public static final int ENROLLMENT_MANAGEMENT = 3;
    public static final int EXIT = 4;

    // Student management options
    public static final int ADD_STUDENT = 1;
    public static final int VIEW_ALL_STUDENTS = 2;
    public static final int SEARCH_STUDENT_BY_ID = 3;
    public static final int DEACTIVATE_STUDENT = 4;
    public static final int BACK_TO_MAIN = 5;

    // Course management options
    public static final int ADD_COURSE = 1;
    public static final int VIEW_ALL_COURSES = 2;
    public static final int ACTIVATE_COURSE = 3;
    public static final int DEACTIVATE_COURSE = 4;
    public static final int BACK_TO_MAIN_COURSE = 5;

    // Enrollment management options
    public static final int ENROLL_STUDENT = 1;
    public static final int VIEW_ENROLLMENTS_BY_STUDENT = 2;
    public static final int MARK_ENROLLMENT_COMPLETED = 3;
    public static final int MARK_ENROLLMENT_CANCELLED = 4;
    public static final int BACK_TO_MAIN_ENROLLMENT = 5;

    // Private constructor to prevent instantiation
    private MenuOptions() {
    }
}

