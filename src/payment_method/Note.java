package payment_method;

import enums.Currency;

public class Note extends PaymentMethod {

    public Note(Currency currency, double value) {
        super(currency, value);
    }

}
