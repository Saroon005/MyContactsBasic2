/**
 * Use Case 4/5 - Create Contact, View Contact Details
 *
 * Base contact model with common fields such as id, name, phone numbers, emails,
 * and created/updated timestamps.
 *
 * UC5 requires a readable contact details view, so this class provides a
 * formatted toString() for console display.
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
    private final List<String> tags;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notes;

    protected Contact(UUID id, String name, List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                      LocalDateTime createdAt, LocalDateTime updatedAt, String notes) {
        this.id = id;
        this.name = name;
        this.phoneNumbers = new ArrayList<>(phoneNumbers == null ? List.of() : phoneNumbers);
        this.emailAddresses = new ArrayList<>(emailAddresses == null ? List.of() : emailAddresses);
        this.tags = new ArrayList<>();
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

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public void addTag(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            return;
        }
        String normalized = tag.trim();
        for (String existing : tags) {
            if (existing != null && existing.equalsIgnoreCase(normalized)) {
                return;
            }
        }
        tags.add(normalized);
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("=== Contact Details ===\n");
        builder.append("Contact ID: ").append(id).append("\n");
        builder.append("Contact Type: ").append(getContactType()).append("\n");
        builder.append("Contact Name: ").append(name).append("\n");

        builder.append("Tags: ");
        if (tags.isEmpty()) {
            builder.append("None\n");
        } else {
            builder.append(String.join(", ", tags)).append("\n");
        }

        builder.append("Phone Numbers: ");
        if (phoneNumbers.isEmpty()) {
            builder.append("None\n");
        } else {
            builder.append("\n");
            for (PhoneNumber phoneNumber : phoneNumbers) {
                builder.append("- ").append(phoneNumber).append("\n");
            }
        }

        builder.append("Email Addresses: ");
        if (emailAddresses.isEmpty()) {
            builder.append("None\n");
        } else {
            builder.append("\n");
            for (EmailAddress emailAddress : emailAddresses) {
                builder.append("- ").append(emailAddress).append("\n");
            }
        }

        builder.append("Notes: ");
        if (notes == null || notes.trim().isEmpty()) {
            builder.append("None\n");
        } else {
            builder.append(notes.trim()).append("\n");
        }

        builder.append("Created At: ").append(createdAt).append("\n");
        builder.append("Last Updated: ").append(updatedAt);
        return builder.toString();
    }
}
