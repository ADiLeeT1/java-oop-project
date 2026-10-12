package carrental.model;

import carrental.exception.RentalOperationException;

/**
 * Оплата аренды.
 *
 * ЗАВИСИМОСТЬ: RentalService создаёт Payment как локальную переменную
 * внутри метода payForRental(), использует его и не хранит в своих полях.
 */
public class Payment {

    public static final String METHOD_CARD = "CARD";
    public static final String METHOD_CASH = "CASH";

    private final int id;
    private final double amount;
    private final String method;
    private boolean completed;

    public Payment(int id, double amount, String method) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }
        this.id = id;
        this.amount = amount;
        this.method = method;
    }

    /**
     * Проводит оплату. throws - метод предупреждает, что может выбросить
     * проверяемое исключение, и вызывающий код обязан его обработать.
     */
    public String process(String cardNumber) throws RentalOperationException {
        if (method.equals(METHOD_CARD)) {
            if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
                throw new RentalOperationException("Card number must contain exactly 16 digits.");
            }
        } else if (!method.equals(METHOD_CASH)) {
            throw new RentalOperationException("Unknown payment method: " + method);
        }
        completed = true;
        return getReceipt(cardNumber);
    }

    private String getReceipt(String cardNumber) {
        if (method.equals(METHOD_CARD)) {
            // В чеке показываем только последние 4 цифры карты
            return "PAY-" + id + " CARD *" + cardNumber.substring(12);
        }
        return "PAY-" + id + " CASH";
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public boolean isCompleted() {
        return completed;
    }
}
