/**
 * User Registration
 *
 * Contains registration input checks such as email, password, and profile validation.
 * Validation rules are intentionally simple for UC1 and kept modular.
 */
package com.mycontacts.user.validation;

import java.util.regex.Pattern;

public final class InputValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private InputValidator() {
    }

    public static boolean isEmailValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isFullNameValid(String fullName) {
        return fullName != null && !fullName.trim().isEmpty();
    }
}
