/**
 * Use Case 1/2/3 - Registration, Login, Profile Management
 *
 * Extending UC1 to implement user login (UC2) and basic profile management (UC3).
 *
 * Console entry point. Registers a user, logs them in using a chosen authentication
 * strategy, then allows simple profile updates.
 * @author developer
 * @version 3.0
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
}
