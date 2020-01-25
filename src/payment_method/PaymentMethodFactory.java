package payment_method;

import enums.Currency;
import enums.PaymentMethodTypes;
import exceptions.NotSupportedException;

public class PaymentMethodFactory {

    private static final PaymentMethodFactory instance = new PaymentMethodFactory();

    public static PaymentMethodFactory getInstance() {
        return instance;
    }

    public PaymentMethod createMoney(PaymentMethodTypes type, Currency currency, double value) {

        switch (type) {
            case CARD:
                return new Card(currency, value);
            case COIN:
                return new Coin(currency, value);
            case NOTE:
                return new Note(currency, value);
            default:
                throw new NotSupportedException(String.format("The %s payment method is not supported", type.name()));
        }
    }
}
