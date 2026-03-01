/**
 * Use Case 4 - Create Contact
 *
 * Small repository contract for storing and retrieving contacts.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.contact.repository;

import java.util.List;

import com.mycontacts.contact.model.Contact;

public interface ContactRepository {
    void addForUser(String userEmail, Contact contact);

    List<Contact> getForUser(String userEmail);
}
