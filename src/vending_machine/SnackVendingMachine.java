package vending_machine;

import display.Display;
import display.SnackVendingMachineDisplay;
import enums.Key;
import exceptions.FullSpaceException;
import exceptions.IllegalActionException;
import exceptions.IllegalValueException;
import exceptions.ItemNotFoundException;
import keypad.KeyPad;
import keypad.SnackVendingMachineKeyPad;
import payment_method.Card;
import payment_method.Coin;
import payment_method.Note;
import payment_method.PaymentMethod;
import payment_system.PaymentSystem;
import payment_system.SnackVendingMachinePaymentSystem;

import java.util.ArrayList;
import java.util.Stack;

public class SnackVendingMachine extends VendingMachine {

    private final int MAX_DEPTH;
    public final int NUM_ROWS = 5;
    public final int NUM_COLS = 5;

    private Key selectedRowKey = Key.KEY_UNKNOWN;
    private Key selectedColKey = Key.KEY_UNKNOWN;

    private double selectedItemPrice = 0;
    private int row;
    private int col;

    public SnackVendingMachine(KeyPad keypad, Display display, PaymentSystem paymentSystem, int max_depth) {
        super(keypad, display, paymentSystem);
        MAX_DEPTH = max_depth;
        prices = new double[NUM_ROWS][NUM_COLS];
        items = new String[NUM_ROWS][NUM_COLS];
        inventory = new Stack[NUM_ROWS][NUM_COLS];

        // create stack slots in the inventory
        for ( int i = 0; i < inventory.length;i++)
            for ( int j = 0 ; j < inventory[i].length;j++)
                inventory[i][j] = new Stack<String>();

        setDisplay();
    }

    private void setDisplay() {
        String isAvailable = "False";

        if (selectedRowKey != Key.KEY_UNKNOWN && selectedColKey != Key.KEY_UNKNOWN) {

            if (inventory[row][col].size() > 0) {
                isAvailable = "True";
            }
        }

        getDisplay().setDisplayContent(
                String.format("ROW:%s COL:%s\nInserted Amount: %.02f, Required Amount: %.02f\nAvailable: %s",
                        selectedRowKey.getStringRepresentation(),
                        selectedColKey.getStringRepresentation(),
                        getPaymentSystem().getInserted(),
                        selectedItemPrice, isAvailable
                )
        );

        System.out.println(getDisplay().getDisplayContent());
        System.out.println("-----------------------------------------------------------------");
    }


    public void initMachine() {

        for (int i = 0; i < NUM_ROWS; i++) {

            for (int j = 0; j < NUM_COLS; j++) {

                fillSlot(i, j, MAX_DEPTH);

            }
        }

        setDisplay();
    }

    public void setSnack(int row, int col, String name, double price) {
        setItem(row, col, name);
        setPrice(row, col, price);
    }


    @Override
    public void fillSlot(int row, int col, int amount) {

        if (amount < 0)
            throw new IllegalValueException("Can't enter negative amount of items");

        while (inventory[row][col].size() != MAX_DEPTH && amount != 0) {
            inventory[row][col].push(items[row][col]);
            amount--;
        }

        if (amount > 0)
            throw new FullSpaceException("Can't add more items, The slot has been filled");
    }

    @Override
    public SnackVendingMachineKeyPad getKeypad() {
        return (SnackVendingMachineKeyPad) super.getKeypad();
    }

    @Override
    public SnackVendingMachineDisplay getDisplay() {
        return (SnackVendingMachineDisplay) super.getDisplay();
    }

    @Override
    public SnackVendingMachinePaymentSystem getPaymentSystem() {
        return (SnackVendingMachinePaymentSystem) super.getPaymentSystem();
    }

    @Override
    public ArrayList<PaymentMethod> pressKey(Key key) {

        if (getKeypad().isRowKey(key)) {
            selectedRowKey = key;
            row = selectedRowKey.getStringRepresentation().charAt(0) - 'A';
        }

        if (getKeypad().isColKey(key)) {
            selectedColKey = key;
            col = Integer.parseInt(selectedColKey.getStringRepresentation()) - 1;
        }

        if (selectedRowKey != Key.KEY_UNKNOWN && selectedColKey != Key.KEY_UNKNOWN) {
            selectedItemPrice = prices[row][col];
        }

        if (key == Key.KEY_OK) {
            return okAction();
        }

        if (key == Key.KEY_CANCEL) {
            return cancelAction();
        }

        setDisplay();

        return null;
    }

    public void insertCoin(Coin coin) {
        getPaymentSystem().insertCoin(coin);
        setDisplay();
    }

    public void insertNote(Note note) {
        getPaymentSystem().insertNote(note);
        setDisplay();
    }

    public void insertCard(Card card) {
        getPaymentSystem().insertCard(card);
        setDisplay();
    }

    private ArrayList<PaymentMethod> cancelAction() {
        resetDisplay();
        return getPaymentSystem().refundAll();
    }

    private ArrayList<PaymentMethod> okAction() {
        ArrayList<PaymentMethod> change = null;

        if (selectedColKey == Key.KEY_UNKNOWN || selectedRowKey == Key.KEY_UNKNOWN) {
            throw new IllegalActionException("You must select an item");
        }

        if (inventory[row][col].size() > 0) {
            Object item = inventory[row][col].pop();
            change =  getPaymentSystem().buy(selectedItemPrice);

            System.out.println(String.format("%s item was bought", item.toString()));
            System.out.println("-----------------------------------------------------------------");

            resetDisplay();
        } else {
            throw new ItemNotFoundException("The requested item is out of stock");
        }

        return change;
    }

    public void resetDisplay() {
        selectedColKey = Key.KEY_UNKNOWN;
        selectedRowKey = Key.KEY_UNKNOWN;
        selectedItemPrice = 0;

        setDisplay();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
