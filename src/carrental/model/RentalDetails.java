package carrental.model;

import java.time.LocalDate;

/**
 * Детали конкретной аренды: даты и итоговая стоимость.
 *
 * КОМПОЗИЦИЯ: объект RentalDetails создаётся ВНУТРИ конструктора Rental
 * и существует только как часть этой аренды. Отдельно от Rental он не нужен
 * и не создаётся.
 */
public class RentalDetails {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int days;
    private final double totalPrice;

    public RentalDetails(int days, double totalPrice) {
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be positive.");
        }
        this.days = days;
        this.totalPrice = totalPrice;
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusDays(days);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDays() {
        return days;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
