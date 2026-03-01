/**
 * Common in-memory repository that stores email -> User.
 * Kept separate so it can be reused later for different retrieval tasks.
 */
package com.mycontacts.user.userrepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mycontacts.user.model.User;

public class UserRepository {
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();

    public void save(User user) {
        if (user == null || user.getEmail() == null) {
            return;
        }
        usersByEmail.put(user.getEmail().toLowerCase(), user);
    }

    public User findByEmail(String email) {
        if (email == null) {
            return null;
        }
        return usersByEmail.get(email.toLowerCase());
    }

    public boolean contains(String email) {
        if (email == null) {
            return false;
        }
        return usersByEmail.containsKey(email.toLowerCase());
    }
}
