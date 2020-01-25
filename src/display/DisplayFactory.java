package display;

import enums.MachineType;
import exceptions.NotSupportedException;

/**
 * Factory object to build new display instances
 */
public class DisplayFactory {

    private static final DisplayFactory instance = new DisplayFactory();

    public static DisplayFactory getInstance() {
        return instance;
    }

    public Display buildDisplay(MachineType machineType) {

        switch (machineType) {
            case SNACK_VENDING_MACHINE:
                return new SnackVendingMachineDisplay();
            default:
                throw new NotSupportedException(
                        String.format("The %s machine type is not supported", machineType.name())
                );
        }
    }
}
