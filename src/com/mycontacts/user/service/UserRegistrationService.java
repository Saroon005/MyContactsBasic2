/**
 * Handles the registration flow: validation of inputs, password hashing, and creation
 * of appropriate User instances (FreeUser / PremiumUser). Keeps logic modular.
 */
package com.mycontacts.user.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mycontacts.user.exception.ValidationException;
import com.mycontacts.user.model.FreeUser;
import com.mycontacts.user.model.PremiumUser;
import com.mycontacts.user.model.User;
import com.mycontacts.user.util.PasswordHasher;
import com.mycontacts.user.validation.InputValidator;

public class UserRegistrationService {
    public User register(String email, String password, String fullName, String accountType) throws ValidationException {
        String normalizedEmail = email == null ? null : email.trim().toLowerCase();
        String normalizedName = fullName == null ? null : fullName.trim();
        String normalizedAccountType = accountType == null ? null : accountType.trim().toLowerCase();

        if (!InputValidator.isEmailValid(normalizedEmail)) {
            throw new ValidationException("Invalid email format.");
        }

        if (!InputValidator.isPasswordValid(password)) {
            throw new ValidationException("Password must be at least 6 characters.");
        }

        if (!InputValidator.isFullNameValid(normalizedName)) {
            throw new ValidationException("Full name cannot be empty.");
        }

        String passwordHash = PasswordHasher.hash(password);
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        if ("free".equals(normalizedAccountType)) {
            return new FreeUser(userId, normalizedEmail, passwordHash, normalizedName, createdAt);
        }

        if ("premium".equals(normalizedAccountType)) {
            return new PremiumUser(userId, normalizedEmail, passwordHash, normalizedName, createdAt);
        }

        throw new ValidationException("Account type must be either free or premium.");
    }
}
