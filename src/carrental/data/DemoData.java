package carrental.data;

import carrental.model.Car;
import carrental.model.EconomyCar;
import carrental.model.ElectricCar;
import carrental.model.Garage;
import carrental.model.Suv;

/**
 * Начальные данные: машины автопарка.
 * Вынесено из Main, чтобы Main только запускал приложение.
 */
public class DemoData {

    public Garage createGarage() {
        Garage garage = new Garage("Almaty Car Rental");

        // ПОЛИМОРФИЗМ: переменная типа Car (родитель), объект - наследник
        Car car1 = new EconomyCar(1, "Toyota", "Corolla", 2021, 15000);
        Car car2 = new EconomyCar(2, "Hyundai", "Accent", 2022, 13000);
        Car car3 = new EconomyCar(3, "Kia", "Rio", 2023, 14000);
        Car car4 = new Suv(4, "Toyota", "Land Cruiser", 2022, 45000, 7000);
        Car car5 = new Suv(5, "Kia", "Sportage", 2023, 28000, 5000);
        Car car6 = new ElectricCar(6, "Tesla", "Model 3", 2023, 35000, 500);
        Car car7 = new ElectricCar(7, "BYD", "Han", 2024, 30000, 550);

        // АГРЕГАЦИЯ: машины созданы отдельно и только добавляются в гараж
        garage.addCar(car1);
        garage.addCar(car2);
        garage.addCar(car3);
        garage.addCar(car4);
        garage.addCar(car5);
        garage.addCar(car6);
        garage.addCar(car7);
        return garage;
    }
}
