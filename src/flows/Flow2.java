package flows;

import enums.Currency;
import enums.Key;
import enums.MachineType;
import enums.PaymentMethodTypes;
import payment_method.Card;
import payment_method.Note;
import payment_method.PaymentMethodFactory;
import vending_machine.SnackVendingMachine;
import vending_machine.VendingMachineFactory;

// In this flow the user cancels the buy operation
public class Flow2 {

    public static void main(String[] args) {
        SnackVendingMachine snackVendingMachine = (SnackVendingMachine) VendingMachineFactory.getInstance()
                .buildVendingMachine(MachineType.SNACK_VENDING_MACHINE, Currency.USD, 10);

        for (int i = 0; i < snackVendingMachine.NUM_ROWS; i++) {

            for (int j = 0; j < snackVendingMachine.NUM_COLS; j++) {

                snackVendingMachine.setSnack(
                        i, j, String.format("Item_%d_%d", i+1, j+1), i + 1
                );

            }
        }

        snackVendingMachine.initMachine();

        snackVendingMachine.pressKey(Key.KEY_C);
        snackVendingMachine.pressKey(Key.KEY_5);

        snackVendingMachine.insertNote(
                (Note) PaymentMethodFactory.getInstance().createMoney(
                        PaymentMethodTypes.NOTE,
                        Currency.USD,
                        20.0
                )
        );

        snackVendingMachine.insertCard(
                (Card) PaymentMethodFactory.getInstance().createMoney(
                        PaymentMethodTypes.CARD,
                        Currency.USD,
                        120.0
                )
        );

        System.out.println(snackVendingMachine.pressKey(Key.KEY_CANCEL));
    }
}
