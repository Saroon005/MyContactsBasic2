/**
 * Use Case 2 - User Authentication
 *
 * Basic authentication strategy that validates an email/password pair against the
 * `InMemoryAuthStore`. Passwords are hashed with the same `PasswordHasher` used at
 * registration.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.user.auth;

import com.mycontacts.user.model.User;
import com.mycontacts.user.userrepository.UserRepository;
import com.mycontacts.user.util.PasswordHasher;

public class BasicAuthStrategy implements Authentication {
    private final UserRepository store;

    public BasicAuthStrategy(UserRepository store) {
        this.store = store;
    }

    @Override
    public boolean authenticate(String email, String password) {
        if (email == null || password == null) return false;
        User user = store.findByEmail(email);
        if (user == null) return false;
        return user.getPasswordHash().equals(PasswordHasher.hash(password));
    }
}
