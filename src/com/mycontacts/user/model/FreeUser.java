/**
 * Concrete `FreeUser` extending the base User model. Represents a free account.
 */
package com.mycontacts.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FreeUser extends User {

    public FreeUser(UUID id, String email, String passwordHash, String fullName, LocalDateTime createdAt) {
        super(id, email, passwordHash, fullName, createdAt);
    }

    @Override
    public String getAccountType() {
        return "FREE";
    }
}