package vending_machine;

import enums.Currency;
import enums.Key;
import enums.MachineType;
import enums.PaymentMethodTypes;
import exceptions.*;
import org.junit.Test;
import payment_method.*;
import payment_system.PaymentSystem;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VendingMachineTest {

    @Test
    public void snackVendingMachineTest() {
        SnackVendingMachine snackVendingMachine = (SnackVendingMachine) VendingMachineFactory.
                getInstance().buildVendingMachine(MachineType.SNACK_VENDING_MACHINE, Currency.USD, 5);


        for (int i = 0; i < snackVendingMachine.NUM_ROWS; i++) {

            for (int j = 0; j < snackVendingMachine.NUM_COLS; j++) {

                snackVendingMachine.setSnack(
                        i, j, String.format("Item_%d_%d", i+1, j+1), i + 1
                );

            }
        }

        Card card = (Card) PaymentMethodFactory.getInstance().createMoney(
                PaymentMethodTypes.CARD, Currency.USD, 1.7
        );

        snackVendingMachine.insertCard(card);

        assertThrows(CardSlotAlreadyInUseException.class, () -> {
            snackVendingMachine.insertCard(card);
        });

        assertEquals(1.7, snackVendingMachine.getPaymentSystem().getInserted(), 0.0);

        assertThrows(UnknownKeyException.class, () -> {
            snackVendingMachine.pressKey(Key.KEY_UNKNOWN);
        });

        snackVendingMachine.pressKey(Key.KEY_C);

        assertEquals(2, snackVendingMachine.getRow());

        assertThrows(IllegalActionException.class, () -> {
            snackVendingMachine.pressKey(Key.KEY_OK);
        });

        snackVendingMachine.pressKey(Key.KEY_2);

        assertEquals(1, snackVendingMachine.getCol());

        assertThrows(ItemNotFoundException.class, () -> {
            snackVendingMachine.pressKey(Key.KEY_OK);
        });

        snackVendingMachine.initMachine();

        snackVendingMachine.insertNote(
                (Note) PaymentMethodFactory.getInstance().createMoney(
                        PaymentMethodTypes.NOTE, Currency.USD, 50
                )
        );

        ArrayList<PaymentMethod> change = snackVendingMachine.pressKey(Key.KEY_OK);

        assertEquals(13, change.size());
        assertEquals(card, change.get(change.size() - 1));

        for (int i = 0; i < 2; i++) {
            assertEquals(
                    Note.class,
                    change.get(i).getClass()
            );

            assertEquals(
                    20.0,
                    change.get(i).getValue(),
                    0.0
            );
        }

        for (int i = 2; i < 10; i++) {
            assertEquals(
                    Coin.class,
                    change.get(i).getClass()
            );

            assertEquals(
                    1.0,
                    change.get(i).getValue(),
                    0.0
            );
        }

        assertEquals(
                Coin.class,
                change.get(10).getClass()
        );

        assertEquals(
                0.5,
                change.get(10).getValue(),
                0.0
        );

        assertEquals(
                Coin.class,
                change.get(11).getClass()
        );

        assertEquals(
                0.2,
                change.get(11).getValue(),
                0.0
        );
    }
}
