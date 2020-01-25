package slot;

import enums.Currency;
import enums.Denomination;
import enums.PaymentMethodTypes;
import exceptions.NotSupportedException;
import payment_method.PaymentMethod;
import payment_method.PaymentMethodFactory;

public class SlotFactory {

    private static final SlotFactory instance = new SlotFactory();

    public static SlotFactory getInstance() {
        return instance;
    }

    public Slot buildSlot(PaymentMethodTypes paymentMethod, Currency currency) {

        PaymentMethod basicPaymentMethod = PaymentMethodFactory.getInstance().createMoney(paymentMethod, currency, 0);

        if (currency != Currency.USD) {
            throw new NotSupportedException(
                    String.format("%s currency is yet to be supported in the slots", currency.name()));
        }

        // each currency requires special denominations so all are ignored for now
        // Only the USD is supported for now
        switch (paymentMethod) {
            case NOTE:
                return new NoteSlot(
                        basicPaymentMethod,
                        Denomination.DENOMINATION_20, Denomination.DENOMINATION_50
                );
            case COIN:
                return new CoinSlot(
                        basicPaymentMethod,
                        Denomination.DENOMINATION_TENTH, Denomination.DENOMINATION_FIFTH,
                        Denomination.DENOMINATION_HALF, Denomination.DENOMINATION_1
                );
            case CARD:
                return new CardSlot(
                        basicPaymentMethod
                );
            default:
                throw new NotSupportedException(
                        String.format("%s payment method is yet to be supported", paymentMethod.name()));
        }
    }
}
