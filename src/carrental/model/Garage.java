package carrental.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Автопарк компании - хранит список машин.
 *
 * АГРЕГАЦИЯ: Garage содержит машины, но машины создаются снаружи
 * (в DemoData) и добавляются через addCar(). Машина может существовать
 * без гаража, а если удалить гараж - объекты Car останутся.
 */
public class Garage {

    private final String name;
    private final List<Car> cars = new ArrayList<>();

    public Garage(String name) {
        this.name = name;
    }

    public void addCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car must not be null.");
        }
        cars.add(car);
    }

    public Car findById(int id) {
        for (Car car : cars) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

    public List<Car> getAvailableCars() {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable()) {
                result.add(car);
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }
}
