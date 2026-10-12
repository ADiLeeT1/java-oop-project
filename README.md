# Almaty Car Rental — Java OOP Project

A console car rental system written in pure Java (Project 1: application core and business logic).

**Flow:** Register → Login → Main menu → View cars → Rent a car → Pay → Return / Cancel → Rental history → Logout

## How to run

1. Open the project folder in IntelliJ IDEA (JDK 21).
2. Open `src/carrental/Main.java` and click **Run**.

## Features

- Registration and login with username/password check
- List of available cars (printed as a table with `printf`)
- Renting a car for 1–30 days with automatic price calculation
- Payment by card (16-digit check) or cash, with a receipt
- Returning a car and cancelling an unpaid rental
- Rental history with total amount paid
- Error handling: the program never crashes on wrong input

## Project structure

```
src/carrental/
├── Main.java                     entry point, only starts the app
├── ui/ConsoleMenu.java           Scanner input, menus (no business logic)
├── service/
│   ├── AuthService.java          registration and login
│   ├── CarService.java           available cars, car lookup
│   └── RentalService.java        rent, pay, return, cancel, history
├── model/
│   ├── User.java                 user with private fields
│   ├── Car.java                  abstract car
│   ├── EconomyCar.java           -10% for 7+ days
│   ├── Suv.java                  + insurance per day
│   ├── ElectricCar.java          + one-time charging fee
│   ├── Rental.java               main operation
│   ├── RentalDetails.java        dates and total price of a rental
│   ├── Payment.java              payment processing
│   └── Garage.java               car fleet
├── data/DemoData.java            initial cars
└── exception/
    ├── RentalSystemException.java   base checked exception
    ├── AuthException.java
    └── RentalOperationException.java
```

## OOP concepts in the code

| Concept | Where |
|---|---|
| Classes and objects | All classes in `model`; objects created in `DemoData`, `RentalService`, `Main` |
| Constructors | Every model class; `super(...)` in `EconomyCar`, `Suv`, `ElectricCar` |
| Encapsulation, getters/setters | `User`, `Car` — private fields, setters validate values |
| Inheritance | `Car` → `EconomyCar`, `Suv`, `ElectricCar`; `RentalSystemException` → `AuthException`, `RentalOperationException` |
| Abstraction | `abstract class Car` with abstract `getType()` and `calculateRentalPrice()` |
| Polymorphism | `Car car = new Suv(...)` in `DemoData`; `car.calculateRentalPrice(days)` in `Rental` |
| `@Override` | `getType()`, `calculateRentalPrice()`, `getExtraInfo()`, `toString()` |
| Association | `Rental` ↔ `User`, `Rental` → `Car` |
| Aggregation | `Garage` holds `Car` objects created outside of it |
| Composition | `Rental` creates its own `RentalDetails` in the constructor |
| Dependency | `RentalService.payForRental()` creates and uses a local `Payment` |
| Exceptions | custom checked exceptions; `throw`/`throws` in services; `try/catch` in `ConsoleMenu`; `finally` in `Main` |
| Scanner | `ConsoleMenu` |
