package slot;

import enums.Denomination;
import payment_method.PaymentMethod;

public class NoteSlot extends Slot {

    public NoteSlot(PaymentMethod basicAcceptablePaymentMethod, Denomination... acceptableDenominationList) {
        super(basicAcceptablePaymentMethod, acceptableDenominationList);
    }

}
