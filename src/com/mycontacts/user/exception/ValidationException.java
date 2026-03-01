/**
 * Custom checked exception used to indicate validation problems during registration.
 * Keeps error handling explicit and localized to UC1 operations.
 */
package com.mycontacts.user.exception;

public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
