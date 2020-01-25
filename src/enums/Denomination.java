package enums;

public enum Denomination {
    DENOMINATION_TENTH(0.10),
    DENOMINATION_FIFTH(0.20),
    DENOMINATION_HALF(0.50),
    DENOMINATION_1(1.0),
    DENOMINATION_20(20.0),
    DENOMINATION_50(50.0),
    DENOMINATION_100(100.0);

    private double value;

    Denomination(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
