package display;

import enums.MachineType;
import exceptions.NotSupportedException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DisplayFactoryTest {

    @Test
    public void displayFactoryTest() {
        Display display = DisplayFactory.getInstance().buildDisplay(MachineType.SNACK_VENDING_MACHINE);

        assertTrue(display instanceof SnackVendingMachineDisplay);

        assertThrows(NotSupportedException.class,
                () -> {DisplayFactory.getInstance().buildDisplay(MachineType.UNKNOWN_VENDING_MACHINE);});
    }

}
