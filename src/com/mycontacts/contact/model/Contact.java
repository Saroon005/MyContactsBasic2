/**
 * Use Case 4 - Create Contact
 *
 * Base contact model with common fields such as id, name, phone numbers, emails,
 * and created/updated timestamps.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.contact.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Contact {
    private final UUID id;
    private String name;
    private final List<PhoneNumber> phoneNumbers;
    private final List<EmailAddress> emailAddresses;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notes;

    protected Contact(UUID id, String name, List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                      LocalDateTime createdAt, LocalDateTime updatedAt, String notes) {
        this.id = id;
        this.name = name;
        this.phoneNumbers = new ArrayList<>(phoneNumbers == null ? List.of() : phoneNumbers);
        this.emailAddresses = new ArrayList<>(emailAddresses == null ? List.of() : emailAddresses);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.notes = notes;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        touch();
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return Collections.unmodifiableList(phoneNumbers);
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null) return;
        phoneNumbers.add(phoneNumber);
        touch();
    }

    public List<EmailAddress> getEmailAddresses() {
        return Collections.unmodifiableList(emailAddresses);
    }

    public void addEmailAddress(EmailAddress emailAddress) {
        if (emailAddress == null) return;
        emailAddresses.add(emailAddress);
        touch();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        touch();
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public abstract String getContactType();
}
