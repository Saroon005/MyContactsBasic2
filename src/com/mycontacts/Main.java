/**
 * Use Case 2 - User Registration and Login
 *
 * Extending UC1 to implement user login.
 *
 * Console entry point. First registers a user (UC1), then allows a simple login
 * simulation (UC2) using different authentication strategies.
 * @author developer
 * @version 2.0
 */
package com.mycontacts;

import java.util.Scanner;

import com.mycontacts.user.auth.Authentication;
import com.mycontacts.user.auth.BasicAuthStrategy;
import com.mycontacts.user.auth.OAuthStrategy;
import com.mycontacts.user.exception.ValidationException;
import com.mycontacts.user.model.User;
import com.mycontacts.user.service.UserRegistrationService;
import com.mycontacts.user.userrepository.UserRepository;

public class Main {

    public static void main(String[] args) {
        UserRegistrationService registrationService = new UserRegistrationService();
        UserRepository userRepository = new UserRepository();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== MyContacts | UC1: User Registration ===");

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

                System.out.println("\n=== MyContacts | UC2: User Login ===");
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
                } else {
                    System.out.println("\nLogin failed.");
                }
            } catch (ValidationException exception) {
                System.out.println("\nRegistration failed: " + exception.getMessage());
            } catch (Exception exception) {
                System.out.println("\nSomething went wrong during registration.");
            }
        }
    }
}
