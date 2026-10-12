package carrental;

import carrental.data.DemoData;
import carrental.model.Garage;
import carrental.service.AuthService;
import carrental.service.CarService;
import carrental.service.RentalService;
import carrental.ui.ConsoleMenu;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Точка входа. Main только собирает объекты и запускает приложение -
 * вся логика находится в сервисах и моделях.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Garage garage = new DemoData().createGarage();
            CarService carService = new CarService(garage);
            RentalService rentalService = new RentalService(carService);
            AuthService authService = new AuthService();

            ConsoleMenu menu = new ConsoleMenu(scanner, authService, carService, rentalService);
            menu.start();
        } catch (NoSuchElementException e) {
            System.out.println("\nInput was closed. Goodbye!");
        } finally {
            // finally выполняется всегда - закрываем Scanner в любом случае
            scanner.close();
        }
    }
}
