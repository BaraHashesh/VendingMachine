package payment_system;

import enums.Denomination;

import java.util.HashMap;

abstract public class PaymentSystem {

    // This map is mapping the denominations of the coin to how many copes/items of it the payment system have
    HashMap<Denomination, Integer> denominationMap = new HashMap<>();
    private double inserted = 0;

    public double getInserted() {
        return inserted;
    }

    public void setInserted(double inserted) {
        this.inserted = inserted;
    }
}
