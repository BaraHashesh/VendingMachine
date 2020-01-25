package payment_method;

import enums.Currency;

public class Coin extends PaymentMethod {

    public Coin(Currency currency, double value) {
        super(currency, value);
    }

}
