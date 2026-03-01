/**
 * Use Case 9 - Search Contacts
 *
 * Extending UC8 to implement searching contacts by name, phone, email, or tags.
 *
 * Console entry point. Registers a user, logs them in using a chosen authentication
 * strategy, then allows profile updates and contact operations.
 * @author developer
 * @version 9.0
 * 
 */
package com.mycontacts;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import com.mycontacts.contact.model.EmailAddress;
import com.mycontacts.contact.model.PhoneNumber;
import com.mycontacts.contact.model.Contact;
import com.mycontacts.contact.repository.InMemoryContactRepository;
import com.mycontacts.contact.service.ContactService;
import com.mycontacts.user.auth.Authentication;
import com.mycontacts.user.auth.BasicAuthStrategy;
import com.mycontacts.user.auth.OAuthStrategy;
import com.mycontacts.user.exception.ValidationException;
import com.mycontacts.user.model.User;
import com.mycontacts.user.service.UserProfileService;
import com.mycontacts.user.service.UserRegistrationService;
import com.mycontacts.user.userrepository.UserRepository;

public class Main {

    public static void main(String[] args) {
        UserRegistrationService registrationService = new UserRegistrationService();
        UserRepository userRepository = new UserRepository();
        UserProfileService profileService = new UserProfileService(userRepository);
        ContactService contactService = new ContactService(new InMemoryContactRepository());

        try (Scanner scanner = new Scanner(System.in)) {
            String choice;
            do {
                System.out.println("\n=== Main Menu ===");
                System.out.println("1) Register");
                System.out.println("2) Login");
                System.out.println("0) Exit");
                System.out.print("Choose an option: ");
                choice = scanner.nextLine();

                if ("1".equals(choice)) {
                    System.out.println("\n=== User Registration ===");

                    System.out.print("Enter full name: ");
                    String fullName = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Choose account type (free/premium): ");
                    String accountType = scanner.nextLine();

                    System.out.println("Password rules: minimum 6 characters.");
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    try {
                        User registeredUser = registrationService.register(email, password, fullName, accountType);
                        userRepository.save(registeredUser);
                        System.out.println("\nRegistration successful.");
                        System.out.println("Welcome, " + registeredUser.getFullName() + "!");
                        System.out.println("Account type: " + registeredUser.getAccountType());
                        System.out.println("Registered email: " + registeredUser.getEmail());
                        System.out.println("User ID: " + registeredUser.getId());
                    } catch (ValidationException exception) {
                        System.out.println("\nRegistration failed: " + exception.getMessage());
                    } catch (Exception exception) {
                        System.out.println("\nSomething went wrong during registration.");
                    }
                } else if ("2".equals(choice)) {
                    System.out.println("\n=== User Login ===");
                    System.out.print("Login method (basic/oauth): ");
                    String loginMethod = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String loginEmail = scanner.nextLine();

                    Authentication authentication;
                    String credential;

                    if (loginMethod != null && loginMethod.trim().equalsIgnoreCase("oauth")) {
                        authentication = new OAuthStrategy(userRepository);
                        System.out.print("Enter OAuth token (try VALID_OAUTH_TOKEN): ");
                        credential = scanner.nextLine();
                    } else {
                        authentication = new BasicAuthStrategy(userRepository);
                        System.out.print("Enter password: ");
                        credential = scanner.nextLine();
                    }

                    boolean isAuthenticated = authentication.authenticate(loginEmail, credential);
                    if (isAuthenticated) {
                        System.out.println("\nLogin successful.");

                        User loggedInUser = profileService.getUser(loginEmail);
                        if (loggedInUser == null) {
                            System.out.println("Logged in user not found in repository.");
                            continue;
                        }

                        String profileChoice;
                        do {
                            System.out.println("\n=== User Profile Management ===");
                            System.out.println("1) Update full name");
                            System.out.println("2) Change password");
                            System.out.println("3) Update notifications preference");
                            System.out.println("4) View profile");
                            System.out.println("5) Create contact (UC4)");
                            System.out.println("6) View contact details (UC5)");
                            System.out.println("7) Edit contact (UC6)");
                            System.out.println("8) Delete contact (UC7)");
                            System.out.println("9) Bulk operations (UC8)");
                            System.out.println("10) Search contacts (UC9)");
                            System.out.println("0) Exit");
                            System.out.print("Choose an option: ");
                            profileChoice = scanner.nextLine();

                            try {
                                if ("1".equals(profileChoice)) {
                                    System.out.print("Enter new full name: ");
                                    String newName = scanner.nextLine();
                                    profileService.updateFullName(loginEmail, newName);
                                    System.out.println("Full name updated.");
                                } else if ("2".equals(profileChoice)) {
                                    System.out.println("Password rules: minimum 6 characters.");
                                    System.out.print("Enter new password: ");
                                    String newPassword = scanner.nextLine();
                                    profileService.changePassword(loginEmail, newPassword);
                                    System.out.println("Password updated.");
                                } else if ("3".equals(profileChoice)) {
                                    System.out.print("Enable notifications? (yes/no): ");
                                    String input = scanner.nextLine();
                                    boolean enabled = input != null && input.trim().equalsIgnoreCase("yes");
                                    profileService.updateNotificationsPreference(loginEmail, enabled);
                                    System.out.println("Preference updated.");
                                } else if ("4".equals(profileChoice)) {
                                    User user = profileService.getUser(loginEmail);
                                    System.out.println("\nUser ID: " + user.getId());
                                    System.out.println("User Name: " + user.getFullName());
                                    System.out.println("Email ID: " + user.getEmail());
                                    System.out.println("Subscription Type: " + user.getAccountType());
                                    System.out.println("Notifications Enabled: " + user.isNotificationsEnabled());
                                } else if ("5".equals(profileChoice)) {
                                    System.out.println("\n=== UC4: Create Contact ===");
                                    System.out.print("Contact type (person/organization): ");
                                    String contactType = scanner.nextLine();

                                    System.out.print("Contact name: ");
                                    String contactName = scanner.nextLine();

                                    List<PhoneNumber> phones = new ArrayList<>();
                                    while (true) {
                                        System.out.print("Add phone number (or press Enter to stop): ");
                                        String phone = scanner.nextLine();
                                        if (phone == null || phone.trim().isEmpty()) {
                                            break;
                                        }
                                        phones.add(new PhoneNumber(phone.trim()));
                                    }

                                    List<EmailAddress> emails = new ArrayList<>();
                                    while (true) {
                                        System.out.print("Add email address (or press Enter to stop): ");
                                        String contactEmail = scanner.nextLine();
                                        if (contactEmail == null || contactEmail.trim().isEmpty()) {
                                            break;
                                        }
                                        emails.add(new EmailAddress(contactEmail.trim()));
                                    }

                                    System.out.print("Notes (optional, press Enter to skip): ");
                                    String notes = scanner.nextLine();

                                    Contact created = contactService.createContact(loginEmail, contactType, contactName, phones, emails, notes);
                                    System.out.println("\nContact created successfully.");
                                    System.out.println("Contact ID: " + created.getId());
                                    System.out.println("Contact Type: " + created.getContactType());
                                    System.out.println("Contact Name: " + created.getName());
                                } else if ("6".equals(profileChoice)) {
                                    System.out.println("\n=== UC5: View Contact Details ===");
                                    System.out.print("Enter Contact Name: ");
                                    String contactName = scanner.nextLine();

                                    List<Contact> matches = contactService.findContactsByName(loginEmail, contactName);
                                    if (matches.isEmpty()) {
                                        System.out.println("No contact found with that name.");
                                    } else {
                                        System.out.println();
                                        for (Contact match : matches) {
                                            System.out.println(match);
                                            System.out.println();
                                        }
                                    }
                                } else if ("7".equals(profileChoice)) {
                                    System.out.println("\n=== UC6: Edit Contact ===");
                                    System.out.print("Enter Contact Name: ");
                                    String contactName = scanner.nextLine();

                                    List<Contact> matches = contactService.findContactsByName(loginEmail, contactName);
                                    if (matches.isEmpty()) {
                                        System.out.println("No contact found with that name.");
                                        continue;
                                    }

                                    Contact selectedContact;
                                    if (matches.size() == 1) {
                                        selectedContact = matches.get(0);
                                    } else {
                                        System.out.println("Multiple contacts found with that name:");
                                        for (int i = 0; i < matches.size(); i++) {
                                            Contact match = matches.get(i);
                                            System.out.println((i + 1) + ") " + match.getContactType() + " | " + match.getName() + " | Created: " + match.getCreatedAt());
                                        }

                                        System.out.print("Select contact number to edit: ");
                                        String selectionInput = scanner.nextLine();
                                        int selection;
                                        try {
                                            selection = Integer.parseInt(selectionInput == null ? "" : selectionInput.trim());
                                        } catch (NumberFormatException exception) {
                                            System.out.println("Invalid selection. Please enter a number.");
                                            continue;
                                        }

                                        if (selection < 1 || selection > matches.size()) {
                                            System.out.println("Invalid selection. Choose a number from 1 to " + matches.size() + ".");
                                            continue;
                                        }

                                        selectedContact = matches.get(selection - 1);
                                    }

                                    String editChoice;
                                    do {
                                        System.out.println("\nCurrent Contact:");
                                        System.out.println(selectedContact);

                                        System.out.println("\nEdit Options:");
                                        System.out.println("1) Update contact name");
                                        System.out.println("2) Update notes");
                                        System.out.println("3) Add phone number");
                                        System.out.println("4) Add email address");
                                        System.out.println("0) Back");
                                        System.out.print("Choose an option: ");
                                        editChoice = scanner.nextLine();

                                        if ("1".equals(editChoice)) {
                                            System.out.print("Enter new contact name: ");
                                            String newName = scanner.nextLine();
                                            if (newName == null || newName.trim().isEmpty()) {
                                                System.out.println("Contact name cannot be empty.");
                                            } else {
                                                selectedContact.setName(newName.trim());
                                                System.out.println("Contact name updated.");
                                            }
                                        } else if ("2".equals(editChoice)) {
                                            System.out.print("Enter new notes (press Enter to clear): ");
                                            String newNotes = scanner.nextLine();
                                            selectedContact.setNotes(newNotes);
                                            System.out.println("Notes updated.");
                                        } else if ("3".equals(editChoice)) {
                                            System.out.print("Enter phone number to add: ");
                                            String phone = scanner.nextLine();
                                            if (phone == null || phone.trim().isEmpty()) {
                                                System.out.println("Phone number cannot be empty.");
                                            } else {
                                                selectedContact.addPhoneNumber(new PhoneNumber(phone.trim()));
                                                System.out.println("Phone number added.");
                                            }
                                        } else if ("4".equals(editChoice)) {
                                            System.out.print("Enter email address to add: ");
                                            String emailToAdd = scanner.nextLine();
                                            if (emailToAdd == null || emailToAdd.trim().isEmpty()) {
                                                System.out.println("Email address cannot be empty.");
                                            } else {
                                                selectedContact.addEmailAddress(new EmailAddress(emailToAdd.trim()));
                                                System.out.println("Email address added.");
                                            }
                                        }
                                    } while (!"0".equals(editChoice));
                                } else if ("8".equals(profileChoice)) {
                                    System.out.println("\n=== UC7: Delete Contact ===");
                                    System.out.print("Enter Contact Name: ");
                                    String contactName = scanner.nextLine();

                                    List<Contact> matches = contactService.findContactsByName(loginEmail, contactName);
                                    if (matches.isEmpty()) {
                                        System.out.println("No contact found with that name.");
                                        continue;
                                    }

                                    System.out.println("Found " + matches.size() + " contact(s) with that name.");
                                    System.out.print("Are you sure you want to delete? (yes/no): ");
                                    String confirm = scanner.nextLine();
                                    boolean confirmed = confirm != null && confirm.trim().equalsIgnoreCase("yes");
                                    if (!confirmed) {
                                        System.out.println("Delete cancelled.");
                                        continue;
                                    }

                                    int deletedCount = contactService.deleteContactsByName(loginEmail, contactName);
                                    System.out.println("Deleted " + deletedCount + " contact(s).");
                                } else if ("9".equals(profileChoice)) {
                                    String bulkChoice;
                                    do {
                                        System.out.println("\n=== UC8: Bulk Operations ===");
                                        System.out.println("1) Bulk delete (by contact names)");
                                        System.out.println("2) Bulk tag (by contact names)");
                                        System.out.println("3) Export contacts (print to console)");
                                        System.out.println("0) Back");
                                        System.out.print("Choose an option: ");
                                        bulkChoice = scanner.nextLine();

                                        if ("1".equals(bulkChoice)) {
                                            System.out.print("Enter contact names separated by commas: ");
                                            String namesInput = scanner.nextLine();
                                            List<String> names = parseCommaSeparated(namesInput);
                                            if (names.isEmpty()) {
                                                System.out.println("No contact names provided.");
                                                continue;
                                            }

                                            int totalMatches = 0;
                                            for (String name : names) {
                                                totalMatches += contactService.findContactsByName(loginEmail, name).size();
                                            }

                                            if (totalMatches == 0) {
                                                System.out.println("No matching contacts found.");
                                                continue;
                                            }

                                            System.out.println("Found " + totalMatches + " contact(s) across the provided names.");
                                            System.out.print("Are you sure you want to delete? (yes/no): ");
                                            String confirm = scanner.nextLine();
                                            boolean confirmed = confirm != null && confirm.trim().equalsIgnoreCase("yes");
                                            if (!confirmed) {
                                                System.out.println("Bulk delete cancelled.");
                                                continue;
                                            }

                                            int deleted = 0;
                                            for (String name : names) {
                                                deleted += contactService.deleteContactsByName(loginEmail, name);
                                            }
                                            System.out.println("Deleted " + deleted + " contact(s).");
                                        } else if ("2".equals(bulkChoice)) {
                                            System.out.print("Enter tag to apply: ");
                                            String tag = scanner.nextLine();
                                            if (tag == null || tag.trim().isEmpty()) {
                                                System.out.println("Tag cannot be empty.");
                                                continue;
                                            }

                                            System.out.print("Enter contact names separated by commas: ");
                                            String namesInput = scanner.nextLine();
                                            List<String> names = parseCommaSeparated(namesInput);
                                            if (names.isEmpty()) {
                                                System.out.println("No contact names provided.");
                                                continue;
                                            }

                                            int taggedContacts = 0;
                                            for (String name : names) {
                                                List<Contact> matches = contactService.findContactsByName(loginEmail, name);
                                                for (Contact match : matches) {
                                                    match.addTag(tag);
                                                    taggedContacts++;
                                                }
                                            }

                                            if (taggedContacts == 0) {
                                                System.out.println("No matching contacts found.");
                                            } else {
                                                System.out.println("Tagged " + taggedContacts + " contact(s) with: " + tag.trim());
                                            }
                                        } else if ("3".equals(bulkChoice)) {
                                            List<Contact> contacts = contactService.getContactsForUser(loginEmail);
                                            if (contacts.isEmpty()) {
                                                System.out.println("No contacts to export.");
                                                continue;
                                            }

                                            System.out.println("\n=== Export (Console) ===");
                                            System.out.println("Type | Name | Phones | Emails | Tags | Notes");
                                            for (Contact contact : contacts) {
                                                String phones = joinList(contact.getPhoneNumbers());
                                                String emails = joinList(contact.getEmailAddresses());
                                                String tags = joinList(contact.getTags());
                                                String notes = contact.getNotes() == null ? "" : contact.getNotes().trim();
                                                System.out.println(contact.getContactType() + " | " + contact.getName() + " | " + phones + " | " + emails + " | " + tags + " | " + notes);
                                            }

                                            System.out.println("Exported " + contacts.size() + " contact(s).");
                                        }
                                    } while (!"0".equals(bulkChoice));
                                } else if ("10".equals(profileChoice)) {
                                    System.out.println("\n=== UC9: Search Contacts ===");
                                    System.out.print("Enter search term (name/phone/email/tag): ");
                                    String term = scanner.nextLine();

                                    List<Contact> results = contactService.searchContacts(loginEmail, term);
                                    if (results.isEmpty()) {
                                        System.out.println("No contacts matched your search.");
                                    } else {
                                        System.out.println("Found " + results.size() + " matching contact(s).\n");
                                        for (Contact result : results) {
                                            System.out.println(result);
                                            System.out.println();
                                        }
                                    }
                                }
                            } catch (ValidationException profileException) {
                                System.out.println("\nProfile update failed: " + profileException.getMessage());
                            }
                        } while (!"0".equals(profileChoice));
                    } else {
                        System.out.println("\nLogin failed.");
                    }
                }
            } while (!"0".equals(choice));
        }
    }

    private static List<String> parseCommaSeparated(String input) {
        List<String> result = new ArrayList<>();
        if (input == null) {
            return result;
        }
        String[] parts = input.split(",");
        for (String part : parts) {
            if (part == null) {
                continue;
            }
            String value = part.trim();
            if (!value.isEmpty()) {
                result.add(value);
            }
        }
        return result;
    }

    private static String joinList(List<?> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Object value : values) {
            if (value == null) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(";");
            }
            builder.append(value);
        }
        return builder.toString();
    }
}
