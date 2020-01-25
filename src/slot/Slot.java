package slot;

import enums.Denomination;
import exceptions.SlotSupportException;
import payment_method.PaymentMethod;

import java.util.ArrayList;

abstract public class Slot {

    private PaymentMethod basicAcceptablePaymentMethod;
    private Denomination[] acceptableDenominationList;
    private ArrayList<PaymentMethod> insertedPaymentMethods;
    private double total;

    public Slot(PaymentMethod basicAcceptablePaymentMethod, Denomination... acceptableDenominationList) {
        this.basicAcceptablePaymentMethod = basicAcceptablePaymentMethod;
        this.acceptableDenominationList = acceptableDenominationList;
        this.insertedPaymentMethods = new ArrayList<>();
        this.total = 0;
    }

    public boolean IsAcceptableDomination(PaymentMethod paymentMethod) {
        // check that the money being compared is of the same type is the basic one
        // and that they are part of the same currency
        if (paymentMethod.getClass() == this.basicAcceptablePaymentMethod.getClass()
                && paymentMethod.getCurrency() == basicAcceptablePaymentMethod.getCurrency()) {
            for (Denomination denomination : acceptableDenominationList) {
                if (paymentMethod.getValue() == denomination.getValue()) {
                    return true;
                }
            }
        }

        return false;
    }

    public ArrayList<PaymentMethod> getInsertedPaymentMethods() {
        return this.insertedPaymentMethods;
    }

    public void insertMoney(PaymentMethod paymentMethod) {

        if (IsAcceptableDomination(paymentMethod)) {
            this.insertedPaymentMethods.add(paymentMethod);
            this.total += paymentMethod.getValue();
        } else {
            throw new SlotSupportException(String.format(
                    "The %s payment method does not support the provided currency (Currency: %s, value: %.02f)",
                    this.basicAcceptablePaymentMethod.getClass().getName(), paymentMethod.getCurrency(), paymentMethod.getValue()));
        }
    }

    public ArrayList<PaymentMethod> refund() {
        ArrayList<PaymentMethod> temp = new ArrayList<>(this.insertedPaymentMethods);
        this.clear();
        return temp;
    }

    public void clear() {
        this.insertedPaymentMethods.clear();
        this.total = 0;
    }

    public double getTotal() {
        return total;
    }

    public PaymentMethod getBasicAcceptablePaymentMethod() {
        return basicAcceptablePaymentMethod;
    }

    public Denomination[] getAcceptableDenominationList() {
        return acceptableDenominationList;
    }
}
