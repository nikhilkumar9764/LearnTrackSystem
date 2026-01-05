package com.airtribe.learntrack.exception;

/**
 * Custom exception thrown when user input is invalid.
 */
public class InvalidInputException extends Exception {
    
    public InvalidInputException(String message) {
        super(message);
    }
}

