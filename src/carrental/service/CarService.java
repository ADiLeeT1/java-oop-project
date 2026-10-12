package carrental.service;

import carrental.exception.RentalOperationException;
import carrental.model.Car;
import carrental.model.Garage;

import java.util.List;

/**
 * Работа с машинами: показать доступные, найти машину для аренды.
 */
public class CarService {

    private final Garage garage;

    public CarService(Garage garage) {
        this.garage = garage;
    }

    public void showAvailableCars() {
        List<Car> cars = garage.getAvailableCars();
        if (cars.isEmpty()) {
            System.out.println("No cars available right now.");
            return;
        }
        System.out.printf("%-4s %-10s %-12s %-12s %-6s %14s  %s%n",
                "ID", "Type", "Brand", "Model", "Year", "Price/day", "Notes");
        System.out.println("-".repeat(90));
        // ПОЛИМОРФИЗМ: список типа Car, а внутри разные наследники.
        // getType() и getExtraInfo() вызываются у конкретного класса.
        for (Car car : cars) {
            car.printInfo();
        }
    }

    public Car getAvailableCar(int carId) throws RentalOperationException {
        Car car = garage.findById(carId);
        if (car == null) {
            throw new RentalOperationException("Car with id " + carId + " not found.");
        }
        if (!car.isAvailable()) {
            throw new RentalOperationException(car + " is already rented.");
        }
        return car;
    }
}
