package com.kth.project_dollarstore.exception;

/**
 * Exception for registering, updating user with weak password
 */
public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException(String message) {
        super(message);
    }
}