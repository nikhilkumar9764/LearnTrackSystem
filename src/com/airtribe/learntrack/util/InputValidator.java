package com.airtribe.learntrack.util;

/**
 * Utility class for validating user input.
 * Demonstrates static methods for common validation tasks.
 */
public class InputValidator {
    
    // Private constructor to prevent instantiation
    private InputValidator() {
    }

    /**
     * Validates if a string is not null or empty.
     * @param input the string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Validates if a string can be parsed to an integer.
     * @param input the string to validate
     * @return true if valid integer, false otherwise
     */
    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a string to integer with validation.
     * @param input the string to parse
     * @return parsed integer, or -1 if invalid
     */
    public static int parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Validates email format (basic validation).
     * @param email the email to validate
     * @return true if valid format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }
}

