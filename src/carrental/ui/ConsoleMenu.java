package carrental.ui;

import carrental.exception.AuthException;
import carrental.exception.RentalOperationException;
import carrental.model.Payment;
import carrental.model.Rental;
import carrental.model.User;
import carrental.service.AuthService;
import carrental.service.CarService;
import carrental.service.RentalService;

import java.util.Scanner;

/**
 * Консольный интерфейс: читает ввод через Scanner и вызывает сервисы.
 * Бизнес-логики здесь нет - только меню, ввод и вывод.
 */
public class ConsoleMenu {

    private final Scanner scanner;
    private final AuthService authService;
    private final CarService carService;
    private final RentalService rentalService;
    private User currentUser;

    public ConsoleMenu(Scanner scanner, AuthService authService,
                       CarService carService, RentalService rentalService) {
        this.scanner = scanner;
        this.authService = authService;
        this.carService = carService;
        this.rentalService = rentalService;
    }

    /** Стартовое меню: регистрация, вход, выход. Работает в цикле до выбора 0. */
    public void start() {
        System.out.println("=======================================");
        System.out.println("   Welcome to Almaty Car Rental!");
        System.out.println("=======================================");
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Unknown option. Try again.");
            }
        }
    }

    private void register() {
        System.out.println("--- Registration ---");
        String username = readLine("Username (min 3 chars, no spaces): ");
        String password = readLine("Password (min 4 chars): ");
        String fullName = readLine("Full name: ");
        try {
            User user = authService.register(username, password, fullName);
            System.out.println("Registration successful! Now you can log in, " + user.getFullName() + ".");
        } catch (AuthException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void login() {
        System.out.println("--- Login ---");
        String username = readLine("Username: ");
        String password = readLine("Password: ");
        try {
            currentUser = authService.login(username, password);
            System.out.println("Hello, " + currentUser.getFullName() + "!");
            mainMenu();
        } catch (AuthException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    /** Главное меню после входа. Работает в цикле до выбора Logout. */
    private void mainMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println();
            System.out.println("========== MAIN MENU ==========");
            System.out.println("1. Show available cars");
            System.out.println("2. Rent a car");
            System.out.println("3. Pay for rental");
            System.out.println("4. Return car");
            System.out.println("5. Cancel rental");
            System.out.println("6. My rentals (history)");
            System.out.println("7. Logout");
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1:
                        carService.showAvailableCars();
                        break;
                    case 2:
                        rentCar();
                        break;
                    case 3:
                        payForRental();
                        break;
                    case 4:
                        returnCar();
                        break;
                    case 5:
                        cancelRental();
                        break;
                    case 6:
                        rentalService.showHistory(currentUser);
                        break;
                    case 7:
                        logout();
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("Unknown option. Try again.");
                }
            } catch (RentalOperationException e) {
                // Ошибка бизнес-логики: сообщаем пользователю, программа продолжает работу
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void rentCar() throws RentalOperationException {
        carService.showAvailableCars();
        int carId = readInt("Enter car ID: ");
        int days = readInt("Number of days (1-30): ");
        Rental rental = rentalService.rentCar(currentUser, carId, days);
        System.out.printf("Car booked! Rental #%d: %s for %d days. Total: %,.0f KZT%n",
                rental.getId(), rental.getCar(), days, rental.getDetails().getTotalPrice());
        System.out.println("Don't forget to pay (option 3).");
    }

    private void payForRental() throws RentalOperationException {
        int rentalId = readInt("Enter rental ID to pay: ");
        System.out.println("Payment method: 1 - Card, 2 - Cash");
        int methodChoice = readInt("Choose method: ");
        String method;
        String cardNumber = null;
        if (methodChoice == 1) {
            method = Payment.METHOD_CARD;
            cardNumber = readLine("Card number (16 digits): ").replace(" ", "");
        } else if (methodChoice == 2) {
            method = Payment.METHOD_CASH;
        } else {
            throw new RentalOperationException("Invalid payment method.");
        }
        String receipt = rentalService.payForRental(currentUser, rentalId, method, cardNumber);
        System.out.println("Payment successful! Receipt: " + receipt);
    }

    private void returnCar() throws RentalOperationException {
        int rentalId = readInt("Enter rental ID to return: ");
        rentalService.returnCar(currentUser, rentalId);
        System.out.println("Car returned. Thank you!");
    }

    private void cancelRental() throws RentalOperationException {
        int rentalId = readInt("Enter rental ID to cancel: ");
        rentalService.cancelRental(currentUser, rentalId);
        System.out.println("Rental #" + rentalId + " cancelled.");
    }

    private void logout() {
        if (rentalService.hasActiveRentals(currentUser)) {
            System.out.println("Note: you still have active rentals.");
        }
        System.out.println("Goodbye, " + currentUser.getFullName() + "!");
        currentUser = null;
    }

    /** Читает целое число. Если введено не число - просит повторить, программа не падает. */
    private int readInt(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
