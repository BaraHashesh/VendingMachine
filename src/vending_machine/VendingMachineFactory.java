package vending_machine;

import display.DisplayFactory;
import enums.Currency;
import enums.MachineType;
import exceptions.NotSupportedException;
import keypad.KeyPadFactory;
import payment_system.PaymentSystemFactory;

public class VendingMachineFactory {

    public static final VendingMachineFactory instance = new VendingMachineFactory();

    public static VendingMachineFactory getInstance() {
        return instance;
    }

    public VendingMachine buildVendingMachine(MachineType machineType, Currency currency, int depth) {

        switch (machineType) {
            case SNACK_VENDING_MACHINE:
                return new SnackVendingMachine(
                        KeyPadFactory.getInstance().buildKeyPad(machineType),
                        DisplayFactory.getInstance().buildDisplay(machineType),
                        PaymentSystemFactory.getInstance().buildPaymentSystem(machineType, currency),
                        depth
                );

            default:
                throw new NotSupportedException(String.format("The %s vending machine is yet to be supported",
                        machineType.name()));
        }
    }
}
