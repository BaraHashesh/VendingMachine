package payment_method;

import enums.Currency;

public class Card extends PaymentMethod {

    public Card(Currency currency, double value) {
        super(currency, value);
    }

    private void setBalance(double newBalance) {
        this.setValue(newBalance);
    }

    public boolean haveEnoughBalance(double price) {
        return this.getValue() >= price;
    }

    public void pay(double price) {

        if (haveEnoughBalance(price)) {
            this.setBalance(this.getValue() - price);
        } else {
            this.setBalance(0);
        }
    }
}
