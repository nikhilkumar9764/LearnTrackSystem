package com.airtribe.learntrack.util;

/**
 * Utility class for generating unique IDs.
 * Demonstrates static members and methods.
 */
public class IdGenerator {
    // Static fields to maintain counters across all instances
    private static int studentIdCounter = 1000;
    private static int courseIdCounter = 2000;
    private static int enrollmentIdCounter = 3000;

    // Private constructor to prevent instantiation (utility class pattern)
    private IdGenerator() {
    }

    /**
     * Generates the next unique student ID.
     * @return next student ID
     */
    public static int getNextStudentId() {
        return ++studentIdCounter;
    }

    /**
     * Generates the next unique course ID.
     * @return next course ID
     */
    public static int getNextCourseId() {
        return ++courseIdCounter;
    }

    /**
     * Generates the next unique enrollment ID.
     * @return next enrollment ID
     */
    public static int getNextEnrollmentId() {
        return ++enrollmentIdCounter;
    }

    /**
     * Resets all counters (useful for testing).
     */
    public static void resetCounters() {
        studentIdCounter = 1000;
        courseIdCounter = 2000;
        enrollmentIdCounter = 3000;
    }
}

