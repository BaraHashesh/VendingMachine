package keypad;

import enums.Key;
import exceptions.UnknownKeyException;

public class SnackVendingMachineKeyPad extends KeyPad {

    public static final Key[] COL_KEYS = new Key[]{
            Key.KEY_1, Key.KEY_2, Key.KEY_3, Key.KEY_4, Key.KEY_5,
    };

    public static final Key[] ROW_KEYS = new Key[]{
            Key.KEY_A, Key.KEY_B, Key.KEY_C, Key.KEY_D, Key.KEY_E,
    };

    public static final Key[] CONTROL_KEYS = new Key[]{
            Key.KEY_OK, Key.KEY_CANCEL
    };

    public SnackVendingMachineKeyPad() {
        super(Key.KEY_A, Key.KEY_B, Key.KEY_C, Key.KEY_D, Key.KEY_E,
                Key.KEY_1, Key.KEY_2, Key.KEY_3, Key.KEY_4, Key.KEY_5,
                Key.KEY_OK, Key.KEY_CANCEL);
    }

    public boolean isRowKey(Key pressedKey) {
        validateKey(pressedKey);

        for (Key k : ROW_KEYS) {
            if (k == pressedKey)
                return true;
        }

        return false;
    }

    public boolean isColKey(Key pressedKey) {
        validateKey(pressedKey);

        for (Key k : COL_KEYS) {
            if (k == pressedKey)
                return true;
        }

        return false;
    }

    public boolean isOkKey(Key pressedKey) {
        validateKey(pressedKey);

        return pressedKey == Key.KEY_OK;
    }

    public boolean isCancelKey(Key pressedKey) {
        validateKey(pressedKey);

        return pressedKey == Key.KEY_CANCEL;
    }

    public void validateKey(Key pressedKey) {

        boolean found = false;

        for (Key k : listKeys()) {
            if (k == pressedKey) {
                found = true;
                break;
            }
        }

        if (!found)
            throw new UnknownKeyException(String.format("The %s is an unknown key", pressedKey.name()));
    }

}
