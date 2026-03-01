/**
 * Use Case 4 - Create Contact
 *
 * Value object representing an email address for a contact.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.contact.model;

public class EmailAddress {
    private final String value;

    public EmailAddress(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
