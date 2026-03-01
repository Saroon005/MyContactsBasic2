/**
 * Use Case 4 - Create Contact
 *
 * In-memory repository for contacts using a HashMap-like structure.
 * Stores: userEmail -> List of contacts.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.contact.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
}
