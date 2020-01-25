package payment_method;

import enums.Currency;

// Note it is possible to change the value of the PaymentMethod from a double primitive type
// to a Denomination enum type
abstract public class PaymentMethod {

    private Currency currency;
    private double value;

    public PaymentMethod(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public double getValue() {
        return this.value;
    }

    void setValue(double newValue) {
        this.value = newValue;
    }

    @Override
    public String toString() {
        return String.format("{ %s class, %s currency, %.02f value }", this.getClass().getSimpleName(),
                this.currency.name(), this.value);
    }
}
