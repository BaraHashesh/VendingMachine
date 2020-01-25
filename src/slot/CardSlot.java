package slot;

import enums.Denomination;
import exceptions.CardSlotAlreadyInUseException;
import payment_method.Card;
import payment_method.PaymentMethod;

import java.util.ArrayList;

public class CardSlot extends Slot {

    private Card inUseCard;

    public CardSlot(PaymentMethod basicAcceptablePaymentMethod, Denomination... acceptableDenominationList) {
        super(basicAcceptablePaymentMethod, acceptableDenominationList);
        inUseCard = null;
    }

    @Override
    public boolean IsAcceptableDomination(PaymentMethod paymentMethod) {
        return paymentMethod.getClass() == Card.class
                && this.getBasicAcceptablePaymentMethod().getCurrency() == paymentMethod.getCurrency();
    }

    @Override
    public void insertMoney(PaymentMethod paymentMethod) {

        if (this.inUseCard == null) {
            super.insertMoney(paymentMethod);
            this.inUseCard = (Card) paymentMethod;
        } else {
            throw new CardSlotAlreadyInUseException("Card slot is already in use by a different card");
        }
    }

    @Override
    public ArrayList<PaymentMethod> refund() {
        this.inUseCard = null;
        return super.refund();
    }

    @Override
    public void clear() {
        super.clear();
        this.inUseCard = null;
    }

    public Card getInUseCard() {
        return inUseCard;
    }
}
