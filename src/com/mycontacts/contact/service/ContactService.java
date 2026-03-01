/**
 * Use Case 4 - Create Contact
 *
 * Service for creating contacts for a logged-in user.
 * Uses composition (PhoneNumber, EmailAddress) and stores contacts via repository.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.contact.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.mycontacts.contact.model.Contact;
import com.mycontacts.contact.model.EmailAddress;
import com.mycontacts.contact.model.OrganizationContact;
import com.mycontacts.contact.model.PersonContact;
import com.mycontacts.contact.model.PhoneNumber;
import com.mycontacts.contact.repository.ContactRepository;
import com.mycontacts.user.exception.ValidationException;

public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact createContact(String ownerEmail, String type, String name,
                                 List<PhoneNumber> phones, List<EmailAddress> emails, String notes)
            throws ValidationException {

        if (ownerEmail == null || ownerEmail.trim().isEmpty()) {
            throw new ValidationException("Owner email is required.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Contact name is required.");
        }

        String normalizedType = type == null ? "" : type.trim().toLowerCase();
        LocalDateTime now = LocalDateTime.now();

        Contact contact;
        if ("person".equals(normalizedType)) {
            contact = new PersonContact(UUID.randomUUID(), name.trim(), phones, emails, now, now, notes);
        } else if ("organization".equals(normalizedType) || "org".equals(normalizedType)) {
            contact = new OrganizationContact(UUID.randomUUID(), name.trim(), phones, emails, now, now, notes);
        } else {
            throw new ValidationException("Contact type must be either person or organization.");
        }

        contactRepository.addForUser(ownerEmail, contact);
        return contact;
    }
}
