package enums;

public enum Key {
    KEY_A("A"),
    KEY_B("B"),
    KEY_C("C"),
    KEY_D("D"),
    KEY_E("E"),
    KEY_1("1"),
    KEY_2("2"),
    KEY_3("3"),
    KEY_4("4"),
    KEY_5("5"),
    KEY_OK("OK"),
    KEY_CANCEL("CANCEL"),
    KEY_UNKNOWN("_");

    private String stringRepresentation;

    Key(String a) {
        this.stringRepresentation = a;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }
}
