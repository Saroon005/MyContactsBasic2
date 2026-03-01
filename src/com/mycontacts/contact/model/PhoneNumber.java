/**
 * Use Case 4 - Create Contact
 *
 * Value object representing a phone number for a contact.
 * @author developer
 * @version 1.0
 */
package com.mycontacts.contact.model;

public class PhoneNumber {
    private final String value;

    public PhoneNumber(String value) {
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
