/**
 * Use Case 2 - User Authentication
 *
 * Authentication strategy interface used by different authentication methods
 * (Basic password check, OAuth token simulation). Keeps the code polymorphic.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.user.auth;

public interface Authentication {
    /**
     * Authenticate a user by email using the provided credential.
     * For BasicAuth the credential is a plain password; for OAuth it is a token.
     * @param email user email
     * @param credential password or token depending on strategy
     * @return true if authentication succeeds
     */
    boolean authenticate(String email, String credential);
}
