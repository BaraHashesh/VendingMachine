package display;

import enums.MachineType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DisplayTest {

    @Test
    public void displayTest() {
        SnackVendingMachineDisplay display = (SnackVendingMachineDisplay)
                DisplayFactory.getInstance().buildDisplay(MachineType.SNACK_VENDING_MACHINE);

        assertEquals("", display.getDisplayContent());

        display.setDisplayContent("Hello World!");

        assertEquals("Hello World!", display.getDisplayContent());

        display.appendContent(" Hello");

        assertEquals("Hello World! Hello", display.getDisplayContent());

        display.setDisplayContent("");

        assertEquals("", display.getDisplayContent());
    }

}
