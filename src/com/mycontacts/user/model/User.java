/**
 * Base abstract User model holding encapsulated account details. Extended by
 * concrete account types (FreeUser, PremiumUser). Designed for clarity and encapsulation.
 */
package com.mycontacts.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class User {
    private final UUID id;
    private final String email;
    private final String passwordHash;
    private final String fullName;
    private final LocalDateTime createdAt;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract String getAccountType();
}
