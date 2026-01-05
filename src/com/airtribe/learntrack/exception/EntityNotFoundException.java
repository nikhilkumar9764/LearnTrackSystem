package com.airtribe.learntrack.exception;

/**
 * Custom exception thrown when an entity (Student, Course, Enrollment) is not found.
 */
public class EntityNotFoundException extends Exception {
    
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityType, int id) {
        super(entityType + " with ID " + id + " not found.");
    }
}

