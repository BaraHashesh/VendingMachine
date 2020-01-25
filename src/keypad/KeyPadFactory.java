package keypad;

import enums.MachineType;
import exceptions.NotSupportedException;

public class KeyPadFactory {

    private static final KeyPadFactory instance = new KeyPadFactory();

    public static KeyPadFactory getInstance() {
        return instance;
    }

    public KeyPad buildKeyPad(MachineType machineType) {

        switch (machineType) {
            case SNACK_VENDING_MACHINE:
                return new SnackVendingMachineKeyPad();
            default:
                throw new NotSupportedException(
                        String.format("The %s machine type is not supported", machineType.name())
                );
        }
    }
}
