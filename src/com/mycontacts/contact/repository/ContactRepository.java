/**
 * Use Case 4/5 - Create Contact, View Contact Details
 *
 * Small repository contract for storing and retrieving contacts.
 * UC5 adds lookup by contact id.

 */
package com.mycontacts.contact.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mycontacts.contact.model.Contact;

public interface ContactRepository {
    void addForUser(String userEmail, Contact contact);

    List<Contact> getForUser(String userEmail);

    Optional<Contact> findByIdForUser(String userEmail, UUID contactId);
}
