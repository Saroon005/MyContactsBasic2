/**
 * Use Case 4 - Create Contact
 *
 * Organization contact type.
 */
package com.mycontacts.contact.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrganizationContact extends Contact {

    public OrganizationContact(UUID id, String name, List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                               LocalDateTime createdAt, LocalDateTime updatedAt, String notes) {
        super(id, name, phoneNumbers, emailAddresses, createdAt, updatedAt, notes);
    }

    @Override
    public String getContactType() {
        return "ORGANIZATION";
    }
}
