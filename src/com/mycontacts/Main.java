/**
 * Use Case 1 - User Registration
 *
 * Entry point for the UC1 console app. Gathers registration input, displays
 * password rules, and delegates to the registration service. Kept simple and modular.
 * @author developer
 * @version 1.0
 */
package com.mycontacts;

import java.util.Scanner;

import com.mycontacts.user.exception.ValidationException;
import com.mycontacts.user.model.User;
import com.mycontacts.user.service.UserRegistrationService;

public class Main {

    public static void main(String[] args) {
        UserRegistrationService registrationService = new UserRegistrationService();

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
        }
    }
}
