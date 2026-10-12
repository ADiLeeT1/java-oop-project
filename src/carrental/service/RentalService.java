package carrental.service;

import carrental.exception.RentalOperationException;
import carrental.model.Car;
import carrental.model.Payment;
import carrental.model.Rental;
import carrental.model.User;

import java.util.List;

/**
 * Бизнес-логика аренды: оформить, оплатить, вернуть, отменить, показать историю.
 */
public class RentalService {

    private static final int MAX_DAYS = 30;

    private final CarService carService;
    private int nextRentalId = 1;
    private int nextPaymentId = 1;

    public RentalService(CarService carService) {
        this.carService = carService;
    }

    public Rental rentCar(User user, int carId, int days) throws RentalOperationException {
        if (days < 1 || days > MAX_DAYS) {
            throw new RentalOperationException("Rental period must be from 1 to " + MAX_DAYS + " days.");
        }
        Car car = carService.getAvailableCar(carId);
        Rental rental = new Rental(nextRentalId++, user, car, days);
        car.setAvailable(false);
        user.addRental(rental);
        return rental;
    }

    public String payForRental(User user, int rentalId, String method, String cardNumber)
            throws RentalOperationException {
        Rental rental = findUserRental(user, rentalId);
        if (!rental.getStatus().equals(Rental.STATUS_BOOKED)) {
            throw new RentalOperationException("Rental #" + rentalId + " cannot be paid. Status: "
                    + rental.getStatus());
        }
        // ЗАВИСИМОСТЬ: Payment создаётся и используется только внутри этого метода
        Payment payment = new Payment(nextPaymentId, rental.getDetails().getTotalPrice(), method);
        String receipt = payment.process(cardNumber); // если оплата не прошла - исключение, код ниже не выполнится
        nextPaymentId++;
        rental.markPaid(receipt);
        return receipt;
    }

    public void returnCar(User user, int rentalId) throws RentalOperationException {
        Rental rental = findUserRental(user, rentalId);
        if (!rental.getStatus().equals(Rental.STATUS_PAID)) {
            throw new RentalOperationException("Only paid rentals can be returned. Status: "
                    + rental.getStatus());
        }
        rental.markReturned();
        rental.getCar().setAvailable(true);
    }

    public void cancelRental(User user, int rentalId) throws RentalOperationException {
        Rental rental = findUserRental(user, rentalId);
        if (!rental.getStatus().equals(Rental.STATUS_BOOKED)) {
            throw new RentalOperationException("Only unpaid rentals can be cancelled. Status: "
                    + rental.getStatus());
        }
        rental.markCancelled();
        rental.getCar().setAvailable(true);
    }

    public void showHistory(User user) {
        List<Rental> rentals = user.getRentals();
        if (rentals.isEmpty()) {
            System.out.println("You have no rentals yet.");
            return;
        }
        double totalPaid = 0;
        for (Rental rental : rentals) {
            rental.printInfo();
            if (rental.getPaymentReceipt() != null) {
                totalPaid += rental.getDetails().getTotalPrice();
            }
        }
        System.out.printf("Total rentals: %d | Total paid: %,.0f KZT%n", rentals.size(), totalPaid);
    }

    public boolean hasActiveRentals(User user) {
        for (Rental rental : user.getRentals()) {
            if (rental.isActive()) {
                return true;
            }
        }
        return false;
    }

    private Rental findUserRental(User user, int rentalId) throws RentalOperationException {
        for (Rental rental : user.getRentals()) {
            if (rental.getId() == rentalId) {
                return rental;
            }
        }
        throw new RentalOperationException("Rental #" + rentalId + " not found in your rentals.");
    }
}
