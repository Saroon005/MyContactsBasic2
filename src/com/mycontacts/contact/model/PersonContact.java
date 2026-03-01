/**
 * Use Case 4 - Create Contact
 *
 * Person contact type.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.contact.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PersonContact extends Contact {

    public PersonContact(UUID id, String name, List<PhoneNumber> phoneNumbers, List<EmailAddress> emailAddresses,
                         LocalDateTime createdAt, LocalDateTime updatedAt, String notes) {
        super(id, name, phoneNumbers, emailAddresses, createdAt, updatedAt, notes);
    }

    @Override
    public String getContactType() {
        return "PERSON";
    }
}
