package carrental.model;

/**
 * НАСЛЕДОВАНИЕ: внедорожник - это Car.
 * Особенность: к цене за каждый день добавляется обязательная страховка.
 */
public class Suv extends Car {

    private double insurancePerDay;

    public Suv(int id, String brand, String model, int year, double pricePerDay, double insurancePerDay) {
        super(id, brand, model, year, pricePerDay);
        setInsurancePerDay(insurancePerDay);
    }

    @Override
    public String getType() {
        return "SUV";
    }

    @Override
    public double calculateRentalPrice(int days) {
        return (getPricePerDay() + insurancePerDay) * days;
    }

    @Override
    public String getExtraInfo() {
        return String.format("+%,.0f KZT/day insurance", insurancePerDay);
    }

    public double getInsurancePerDay() {
        return insurancePerDay;
    }

    public void setInsurancePerDay(double insurancePerDay) {
        if (insurancePerDay < 0) {
            throw new IllegalArgumentException("Insurance must not be negative.");
        }
        this.insurancePerDay = insurancePerDay;
    }
}
