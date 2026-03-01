/**
 * Base abstract User model holding encapsulated account details. Extended by
 * concrete account types (FreeUser, PremiumUser). Designed for clarity and encapsulation.
 */
package com.mycontacts.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mycontacts.user.exception.ValidationException;
import com.mycontacts.user.util.PasswordHasher;
import com.mycontacts.user.validation.InputValidator;

public abstract class User {
    private final UUID id;
    private final String email;
    private String passwordHash;
    private String fullName;
    private final LocalDateTime createdAt;

    private boolean notificationsEnabled;

    public User(UUID id, String email, String passwordHash, String fullName, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) throws ValidationException {
        if (!InputValidator.isFullNameValid(fullName)) {
            throw new ValidationException("Full name cannot be empty.");
        }
        this.fullName = fullName.trim();
    }

    public void changePassword(String newPassword) throws ValidationException {
        if (!InputValidator.isPasswordValid(newPassword)) {
            throw new ValidationException("Password must be at least 6 characters.");
        }
        this.passwordHash = PasswordHasher.hash(newPassword);
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract String getAccountType();
}
