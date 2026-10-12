package carrental.exception;

/**
 * Ошибка регистрации или входа:
 * пользователь не найден, неверный пароль, логин уже занят.
 */
public class AuthException extends RentalSystemException {

    public AuthException(String message) {
        super(message);
    }
}
