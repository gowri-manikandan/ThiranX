package com.gowrimanikandan.thiranx;

import com.gowrimanikandan.thiranx.features.signin.SignInView;
import com.gowrimanikandan.thiranx.features.signup.SignUpView;
import com.gowrimanikandan.thiranx.util.ConsoleInput;

import java.util.Scanner;

class ThiranXApplication {

    public static final int VERSION_NO = 3;
    public static final String VERSION_NAME = "2.0.0";

    public static void main(String[] args) {
        System.out.println("Welcome to ThiranX");
        System.out.println("Version " + VERSION_NAME);
        showLandingMenu();
    }

    private static void showLandingMenu() {
        Scanner scanner = ConsoleInput.getScanner();
        while (true) {
            System.out.println();
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new SignUpView().init();
                    break;
                case "2":
                    new SignInView().init();
                    break;
                case "3":
                    System.out.println("Thank you for using ThiranX");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
