package keypad;

import enums.Key;

abstract public class KeyPad {

    private final Key[] KEYS;

    KeyPad(Key... keys) {
        this.KEYS = keys;
    }

    public boolean hasKey(Key key) {
        for (Key value : this.KEYS)
            if (value.equals(key))
                return true;
        return false;
    }

    public Key[] listKeys() {
        return KEYS;
    }

}
