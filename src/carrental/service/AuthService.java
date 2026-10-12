package carrental.service;

import carrental.exception.AuthException;
import carrental.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Регистрация и вход пользователей.
 * Хранит список зарегистрированных пользователей.
 */
public class AuthService {

    private final List<User> users = new ArrayList<>();

    public User register(String username, String password, String fullName) throws AuthException {
        if (findUser(username) != null) {
            throw new AuthException("User '" + username + "' already exists.");
        }
        try {
            User user = new User(username, password, fullName);
            users.add(user);
            return user;
        } catch (IllegalArgumentException e) {
            // Переводим непроверяемое исключение из конструктора User
            // в проверяемое исключение нашей системы
            throw new AuthException("Registration failed: " + e.getMessage());
        }
    }

    public User login(String username, String password) throws AuthException {
        User user = findUser(username);
        if (user == null) {
            throw new AuthException("User '" + username + "' not found.");
        }
        if (!user.checkPassword(password)) {
            throw new AuthException("Wrong password.");
        }
        return user;
    }

    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username.trim())) {
                return user;
            }
        }
        return null;
    }
}
