package carrental.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Пользователь системы (клиент проката).
 *
 * ИНКАПСУЛЯЦИЯ: все поля private, доступ только через getters/setters,
 * а setters проверяют новые значения.
 *
 * АССОЦИАЦИЯ: User связан со своими арендами (Rental).
 * Пользователь и аренда - самостоятельные объекты, которые "знают" друг о друге.
 */
public class User {

    private String username;
    private String password;
    private String fullName;
    private final List<Rental> rentals = new ArrayList<>();

    public User(String username, String password, String fullName) {
        setUsername(username);
        setPassword(password);
        setFullName(fullName);
    }

    /** Проверка пароля без раскрытия самого пароля наружу (getPassword нет специально). */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    /** Возвращаем копию списка, чтобы внешний код не мог изменить историю напрямую. */
    public List<Rental> getRentals() {
        return new ArrayList<>(rentals);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().length() < 3) {
            throw new IllegalArgumentException("Username must contain at least 3 characters.");
        }
        if (username.contains(" ")) {
            throw new IllegalArgumentException("Username must not contain spaces.");
        }
        this.username = username.trim();
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Password must contain at least 4 characters.");
        }
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be empty.");
        }
        this.fullName = fullName.trim();
    }
}
