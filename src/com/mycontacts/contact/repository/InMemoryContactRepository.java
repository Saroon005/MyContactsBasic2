/**
 * Use Case 4/5 - Create Contact, View Contact Details
 *
 * In-memory repository for contacts using a HashMap-like structure.
 * Stores: userEmail -> List of contacts.
 */
package com.mycontacts.contact.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.mycontacts.contact.model.Contact;

public class InMemoryContactRepository implements ContactRepository {
    private final Map<String, List<Contact>> contactsByUserEmail = new ConcurrentHashMap<>();

    @Override
    public void addForUser(String userEmail, Contact contact) {
        if (userEmail == null || contact == null) {
            return;
        }

        String key = userEmail.toLowerCase();
        contactsByUserEmail.computeIfAbsent(key, k -> new ArrayList<>()).add(contact);
    }

    @Override
    public List<Contact> getForUser(String userEmail) {
        if (userEmail == null) {
            return List.of();
        }
        List<Contact> list = contactsByUserEmail.get(userEmail.toLowerCase());
        if (list == null) {
            return List.of();
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public Optional<Contact> findByIdForUser(String userEmail, UUID contactId) {
        if (userEmail == null || contactId == null) {
            return Optional.empty();
        }

        List<Contact> list = contactsByUserEmail.get(userEmail.toLowerCase());
        if (list == null) {
            return Optional.empty();
        }

        for (Contact contact : list) {
            if (contactId.equals(contact.getId())) {
                return Optional.of(contact);
            }
        }
        return Optional.empty();
    }

    @Override
    public int deleteByNameForUser(String userEmail, String contactName) {
        if (userEmail == null || userEmail.trim().isEmpty()) {
            return 0;
        }
        if (contactName == null || contactName.trim().isEmpty()) {
            return 0;
        }

        List<Contact> list = contactsByUserEmail.get(userEmail.toLowerCase());
        if (list == null || list.isEmpty()) {
            return 0;
        }

        String normalizedName = contactName.trim().toLowerCase();

        int beforeSize;
        int afterSize;
        synchronized (list) {
            beforeSize = list.size();
            list.removeIf(c -> c != null
                    && c.getName() != null
                    && c.getName().trim().toLowerCase().equals(normalizedName));
            afterSize = list.size();
        }

        return Math.max(0, beforeSize - afterSize);
    }
}
