package carrental.model;

/**
 * НАСЛЕДОВАНИЕ: эконом-класс - это Car.
 * Особенность: при аренде от 7 дней скидка 10%.
 */
public class EconomyCar extends Car {

    private static final int LONG_RENT_DAYS = 7;
    private static final double LONG_RENT_DISCOUNT = 0.10;

    public EconomyCar(int id, String brand, String model, int year, double pricePerDay) {
        super(id, brand, model, year, pricePerDay); // вызов конструктора родителя
    }

    @Override
    public String getType() {
        return "Economy";
    }

    @Override
    public double calculateRentalPrice(int days) {
        double price = getPricePerDay() * days;
        if (days >= LONG_RENT_DAYS) {
            price = price - price * LONG_RENT_DISCOUNT;
        }
        return price;
    }

    @Override
    public String getExtraInfo() {
        return "-10% from 7 days";
    }
}
