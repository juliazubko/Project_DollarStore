package com.kth.project_dollarstore.exception;

/**
 * Exception for registering with an email already taken.
 */
public class EmailAlreadyTakenException extends RuntimeException {
    
    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}