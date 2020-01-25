package keypad;

import enums.Key;
import enums.MachineType;
import exceptions.UnknownKeyException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeyPadTest {

    @Test
    public void keyPadTest() {
        SnackVendingMachineKeyPad keyPad = (SnackVendingMachineKeyPad) KeyPadFactory.getInstance().buildKeyPad(
                MachineType.SNACK_VENDING_MACHINE
        );

        Key[] keys = SnackVendingMachineKeyPad.ROW_KEYS;

        for(Key k: keys) {
            assertTrue(keyPad.isRowKey(k));
            assertFalse(keyPad.isColKey(k));
            assertFalse(keyPad.isOkKey(k));
            assertFalse(keyPad.isCancelKey(k));
        }

        keys = SnackVendingMachineKeyPad.COL_KEYS;

        for(Key k: keys) {
            assertFalse(keyPad.isRowKey(k));
            assertTrue(keyPad.isColKey(k));
            assertFalse(keyPad.isOkKey(k));
            assertFalse(keyPad.isCancelKey(k));
        }

        assertFalse(keyPad.isRowKey(Key.KEY_OK));
        assertFalse(keyPad.isColKey(Key.KEY_OK));
        assertTrue(keyPad.isOkKey(Key.KEY_OK));
        assertFalse(keyPad.isCancelKey(Key.KEY_OK));

        assertFalse(keyPad.isRowKey(Key.KEY_CANCEL));
        assertFalse(keyPad.isColKey(Key.KEY_CANCEL));
        assertFalse(keyPad.isOkKey(Key.KEY_CANCEL));
        assertTrue(keyPad.isCancelKey(Key.KEY_CANCEL));

        assertThrows(UnknownKeyException.class, () -> {
           keyPad.isColKey(Key.KEY_UNKNOWN);
        });

        assertThrows(UnknownKeyException.class, () -> {
            keyPad.isRowKey(Key.KEY_UNKNOWN);
        });

        assertThrows(UnknownKeyException.class, () -> {
            keyPad.isOkKey(Key.KEY_UNKNOWN);
        });

        assertThrows(UnknownKeyException.class, () -> {
            keyPad.isCancelKey(Key.KEY_UNKNOWN);
        });
    }
}
