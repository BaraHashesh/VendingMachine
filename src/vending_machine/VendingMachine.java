package vending_machine;

import display.Display;
import enums.Key;
import exceptions.IllegalValueException;
import keypad.KeyPad;
import payment_method.PaymentMethod;
import payment_system.PaymentSystem;

import java.util.ArrayList;
import java.util.Stack;

abstract public class VendingMachine {

    Stack[][] inventory;
    double[][] prices;
    String[][] items;
    private KeyPad keypad;
    private Display display;
    private PaymentSystem paymentSystem;

    public VendingMachine(KeyPad keypad, Display display, PaymentSystem paymentSystem) {
        this.keypad = keypad;
        this.display = display;
        this.paymentSystem = paymentSystem;
    }

    public void setPrice(int row, int col, double newPrice) throws IndexOutOfBoundsException {

        if (newPrice <= 0)
            throw new IllegalValueException("Negative & zero prices are not allowed");

        this.prices[row][col] = newPrice;
    }

    public void setItem(int row, int col, String item) throws IndexOutOfBoundsException {
        this.items[row][col] = item;
    }

    public abstract void fillSlot(int row, int col, int amount);

    public abstract void initMachine();

    public KeyPad getKeypad() {
        return keypad;
    }

    public Display getDisplay() {
        return display;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public abstract ArrayList<PaymentMethod> pressKey(Key key);
}
