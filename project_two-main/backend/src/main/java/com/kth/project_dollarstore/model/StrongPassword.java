package com.kth.project_dollarstore.model;

/**
 * This is a helper class for strong password validation.
 * Provides a method to check if a password is valid based on length and character types:
 * - Minimum length of 8 characters
 * - At least one uppercase letter
 * - At least one lowercase letter
 * - At least one digit
 */

public class StrongPassword {

    private static final int MIN_LENGTH = 8;

    public static boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        if (password.length() < MIN_LENGTH) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        return hasUpperCase && hasLowerCase && hasDigit;
    }
}