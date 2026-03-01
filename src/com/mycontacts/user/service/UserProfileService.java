/**
 * Provides simple profile management operations for a logged-in user.
 * Keeps storage separate via UserRepository and keeps validation inside User methods.
 */
package com.mycontacts.user.service;

import com.mycontacts.user.exception.ValidationException;
import com.mycontacts.user.model.User;
import com.mycontacts.user.userrepository.UserRepository;

public class UserProfileService {
    private final UserRepository userRepository;

    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateFullName(String email, String newFullName) throws ValidationException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ValidationException("User not found.");
        }
        user.setFullName(newFullName);
        userRepository.save(user);
    }

    public void changePassword(String email, String newPassword) throws ValidationException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ValidationException("User not found.");
        }
        user.changePassword(newPassword);
        userRepository.save(user);
    }

    public void updateNotificationsPreference(String email, boolean enabled) throws ValidationException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ValidationException("User not found.");
        }
        user.setNotificationsEnabled(enabled);
        userRepository.save(user);
    }
}
