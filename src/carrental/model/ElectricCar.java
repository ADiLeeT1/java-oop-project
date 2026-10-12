package carrental.model;

/**
 * НАСЛЕДОВАНИЕ: электромобиль - это Car.
 * Особенность: разовая плата за полную зарядку + хранит запас хода батареи.
 */
public class ElectricCar extends Car {

    private static final double CHARGING_FEE = 5000;
    private int batteryRangeKm;

    public ElectricCar(int id, String brand, String model, int year, double pricePerDay, int batteryRangeKm) {
        super(id, brand, model, year, pricePerDay);
        setBatteryRangeKm(batteryRangeKm);
    }

    @Override
    public String getType() {
        return "Electric";
    }

    @Override
    public double calculateRentalPrice(int days) {
        return getPricePerDay() * days + CHARGING_FEE;
    }

    @Override
    public String getExtraInfo() {
        return batteryRangeKm + " km range, +5,000 KZT charging";
    }

    public int getBatteryRangeKm() {
        return batteryRangeKm;
    }

    public void setBatteryRangeKm(int batteryRangeKm) {
        if (batteryRangeKm <= 0) {
            throw new IllegalArgumentException("Battery range must be positive.");
        }
        this.batteryRangeKm = batteryRangeKm;
    }
}
