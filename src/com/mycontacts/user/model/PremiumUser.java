/**
 * Concrete `PremiumUser` extending the base User model. Represents a premium account.
 */
package com.mycontacts.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PremiumUser extends User {

    public PremiumUser(UUID id, String email, String passwordHash, String fullName, LocalDateTime createdAt) {
        super(id, email, passwordHash, fullName, createdAt);
    }

    @Override
    public String getAccountType() {
        return "PREMIUM";
    }
}