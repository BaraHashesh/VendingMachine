package slot;

import enums.Denomination;
import payment_method.PaymentMethod;

public class CoinSlot extends Slot {

    public CoinSlot(PaymentMethod basicAcceptablePaymentMethod, Denomination... acceptableDenominationList) {
        super(basicAcceptablePaymentMethod, acceptableDenominationList);
    }
}
