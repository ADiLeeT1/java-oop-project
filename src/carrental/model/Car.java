package carrental.model;

/**
 * АБСТРАКЦИЯ: абстрактный класс "Автомобиль".
 * Создать просто "машину" нельзя (new Car(...) - ошибка компиляции),
 * можно создать только конкретный тип: EconomyCar, Suv или ElectricCar.
 *
 * НАСЛЕДОВАНИЕ: общие поля и методы (id, марка, модель, цена, доступность)
 * описаны здесь один раз и наследуются всеми дочерними классами.
 */
public abstract class Car {

    private final int id;
    private String brand;
    private String model;
    private int year;
    private double pricePerDay;
    private boolean available = true;

    public Car(int id, String brand, String model, int year, double pricePerDay) {
        if (id <= 0) {
            throw new IllegalArgumentException("Car id must be positive.");
        }
        this.id = id;
        setBrand(brand);
        setModel(model);
        setYear(year);
        setPricePerDay(pricePerDay);
    }

    /** Тип машины - каждый наследник обязан вернуть свой. */
    public abstract String getType();

    /**
     * Стоимость аренды на заданное количество дней.
     * У каждого типа машины своя формула - это и есть основа ПОЛИМОРФИЗМА.
     */
    public abstract double calculateRentalPrice(int days);

    /** Дополнительная информация, которую наследники могут переопределить. */
    public String getExtraInfo() {
        return "";
    }

    /** Красивый вывод одной строки таблицы через printf. */
    public void printInfo() {
        System.out.printf("%-4d %-10s %-12s %-12s %-6d %,10.0f KZT  %s%n",
                id, getType(), brand, model, year, pricePerDay, getExtraInfo());
    }

    @Override
    public String toString() {
        return getType() + " " + brand + " " + model + " (" + year + ")";
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand must not be empty.");
        }
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model must not be empty.");
        }
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1990 || year > 2030) {
            throw new IllegalArgumentException("Invalid car year: " + year);
        }
        this.year = year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        if (pricePerDay <= 0) {
            throw new IllegalArgumentException("Price per day must be positive.");
        }
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
