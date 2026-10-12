package carrental.exception;

/**
 * Ошибка бизнес-логики проката:
 * машина не найдена или занята, аренда уже оплачена, неверный способ оплаты и т. д.
 */
public class RentalOperationException extends RentalSystemException {

    public RentalOperationException(String message) {
        super(message);
    }
}
