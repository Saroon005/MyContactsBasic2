/**
 * Use Case 2 - User Authentication
 *
 * Simulated OAuth authentication strategy. This implementation simply checks a
 * hard-coded token to simulate an OAuth provider and ensures the user exists
 * in the in-memory store. This keeps the example simple and focused on strategy
 * polymorphism rather than real OAuth flows.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.user.auth;

import com.mycontacts.user.userrepository.UserRepository;

public class OAuthStrategy implements Authentication {
    private final UserRepository store;
    private static final String SIMULATED_VALID_TOKEN = "VALID_OAUTH_TOKEN";

    public OAuthStrategy(UserRepository store) {
        this.store = store;
    }

    @Override
    public boolean authenticate(String email, String token) {
        if (email == null || token == null) return false;
        if (!SIMULATED_VALID_TOKEN.equals(token)) return false;
        return store.contains(email);
    }
}
