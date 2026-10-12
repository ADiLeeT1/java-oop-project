package carrental.exception;

/**
 * Базовое проверяемое (checked) исключение всей системы проката.
 * Наследуется от Exception, поэтому компилятор заставляет либо
 * обработать его в try/catch, либо объявить в сигнатуре через throws.
 */
public class RentalSystemException extends Exception {

    public RentalSystemException(String message) {
        super(message);
    }
}
