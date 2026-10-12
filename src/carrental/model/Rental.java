package carrental.model;

/**
 * Основная операция предметной области - аренда автомобиля.
 *
 * АССОЦИАЦИЯ: Rental ссылается на User и на Car. Оба объекта существуют
 * независимо от аренды (пользователь и машина остаются после её завершения).
 *
 * КОМПОЗИЦИЯ: Rental сам создаёт свой RentalDetails в конструкторе.
 * Детали - неотъемлемая часть аренды.
 */
public class Rental {

    public static final String STATUS_BOOKED = "BOOKED";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_RETURNED = "RETURNED";
    public static final String STATUS_CANCELLED = "CANCELLED";

    private final int id;
    private final User user;
    private final Car car;
    private final RentalDetails details;
    private String status;
    private String paymentReceipt;

    public Rental(int id, User user, Car car, int days) {
        if (user == null || car == null) {
            throw new IllegalArgumentException("User and car are required for a rental.");
        }
        this.id = id;
        this.user = user;
        this.car = car;
        // КОМПОЗИЦИЯ: часть создаётся владельцем, а не передаётся снаружи.
        // ПОЛИМОРФИЗМ: вызов calculateRentalPrice через ссылку типа Car -
        // выполнится версия EconomyCar, Suv или ElectricCar.
        this.details = new RentalDetails(days, car.calculateRentalPrice(days));
        this.status = STATUS_BOOKED;
    }

    public void markPaid(String receipt) {
        this.status = STATUS_PAID;
        this.paymentReceipt = receipt;
    }

    public void markReturned() {
        this.status = STATUS_RETURNED;
    }

    public void markCancelled() {
        this.status = STATUS_CANCELLED;
    }

    /** Аренда активна, пока машина не возвращена и аренда не отменена. */
    public boolean isActive() {
        return status.equals(STATUS_BOOKED) || status.equals(STATUS_PAID);
    }

    public void printInfo() {
        System.out.printf("#%-3d %-32s %3d days  %s -> %s  %,10.0f KZT  %-9s %s%n",
                id, car.toString(), details.getDays(), details.getStartDate(), details.getEndDate(),
                details.getTotalPrice(), status, paymentReceipt == null ? "" : paymentReceipt);
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public RentalDetails getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentReceipt() {
        return paymentReceipt;
    }
}
