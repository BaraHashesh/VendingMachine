package keypad;

import enums.Key;
import enums.MachineType;
import exceptions.NotSupportedException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeyPadFactoryTest {

    @Test
    public void keyPadFactoryTest() {
        KeyPad keyPad = KeyPadFactory.getInstance().buildKeyPad(MachineType.SNACK_VENDING_MACHINE);

        assertEquals(SnackVendingMachineKeyPad.class, keyPad.getClass());

        assertArrayEquals(keyPad.listKeys(), new Key[]{
                Key.KEY_A, Key.KEY_B, Key.KEY_C, Key.KEY_D, Key.KEY_E,
                Key.KEY_1, Key.KEY_2, Key.KEY_3, Key.KEY_4, Key.KEY_5,
                Key.KEY_OK, Key.KEY_CANCEL
        });

        assertThrows(NotSupportedException.class, () -> {
            KeyPadFactory.getInstance().buildKeyPad(MachineType.UNKNOWN_VENDING_MACHINE);
        });
    }
}
