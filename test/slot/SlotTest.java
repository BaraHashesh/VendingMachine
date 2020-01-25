package slot;

import enums.Currency;
import enums.Denomination;
import enums.PaymentMethodTypes;
import exceptions.CardSlotAlreadyInUseException;
import exceptions.SlotSupportException;
import payment_method.PaymentMethod;
import payment_method.PaymentMethodFactory;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SlotTest {

    @Test
    public void coinSlotTest() {
        CoinSlot coinSlot = (CoinSlot) SlotFactory.getInstance().buildSlot(PaymentMethodTypes.COIN, Currency.USD);

        assertArrayEquals(
                new Denomination[]{
                        Denomination.DENOMINATION_TENTH, Denomination.DENOMINATION_FIFTH,
                        Denomination.DENOMINATION_HALF, Denomination.DENOMINATION_1
                },
                coinSlot.getAcceptableDenominationList());

        assertThrows(SlotSupportException.class, () -> {
            coinSlot.insertMoney(
                    PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.COIN, Currency.NIS, 0.1)
            );
        });

        assertThrows(SlotSupportException.class, () -> {
            coinSlot.insertMoney(
                    PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.COIN, Currency.USD, 2)
            );
        });

        coinSlot.insertMoney(
                PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.COIN, Currency.USD, 0.1)
        );
        coinSlot.insertMoney(
                PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.COIN, Currency.USD, 1)
        );
        coinSlot.insertMoney(
                PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.COIN, Currency.USD, 0.5)
        );

        assertEquals(1.6, coinSlot.getTotal(), 0.0);

        ArrayList<PaymentMethod> refundedPaymentMethod = coinSlot.refund();

        assertEquals(refundedPaymentMethod.size(), 3);
        assertEquals(refundedPaymentMethod.get(0).getValue(), 0.1, 0.0);
        assertEquals(refundedPaymentMethod.get(1).getValue(), 1.0, 0.0);
        assertEquals(refundedPaymentMethod.get(2).getValue(), 0.5, 0.0);

        assertEquals(0.0, coinSlot.getTotal(), 0.0);
    }

    @Test
    public void noteSlotTest() {
        NoteSlot noteSlot = (NoteSlot)SlotFactory.getInstance().buildSlot(PaymentMethodTypes.NOTE, Currency.USD);

        assertThrows(SlotSupportException.class, () -> {
            noteSlot.insertMoney(
                    PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE, Currency.USD, 2)
            );
        });

        assertThrows(SlotSupportException.class, () -> {
            noteSlot.insertMoney(
                    PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE, Currency.NIS, 20)
            );
        });

        assertThrows(SlotSupportException.class, () -> {
            noteSlot.insertMoney(
                    PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.COIN, Currency.USD, 20)
            );
        });

        assertThrows(SlotSupportException.class, () -> {
            noteSlot.insertMoney(
                    PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.CARD, Currency.USD, 20)
            );
        });

        assertArrayEquals(new Denomination[]{Denomination.DENOMINATION_20, Denomination.DENOMINATION_50},
                noteSlot.getAcceptableDenominationList());

        noteSlot.insertMoney(
                PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE, Currency.USD, 20)
        );
        noteSlot.insertMoney(
                PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE, Currency.USD, 20)
        );
        noteSlot.insertMoney(
                PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE, Currency.USD, 50)
        );
        noteSlot.insertMoney(
                PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE, Currency.USD, 20)
        );

        assertEquals(110.0, noteSlot.getTotal(), 0.0);

        ArrayList<PaymentMethod> refundedPaymentMethod = noteSlot.refund();

        assertEquals(refundedPaymentMethod.size(), 4);
        assertEquals(refundedPaymentMethod.get(0).getValue(), 20.0, 0.0);
        assertEquals(refundedPaymentMethod.get(1).getValue(), 20.0, 0.0);
        assertEquals(refundedPaymentMethod.get(2).getValue(), 50.0, 0.0);
        assertEquals(refundedPaymentMethod.get(3).getValue(), 20.0, 0.0);

        assertEquals(0.0, noteSlot.getTotal(), 0.0);
    }

    @Test
    public void cardSlotTest() {

        CardSlot cardSlot = (CardSlot)SlotFactory.getInstance().buildSlot(PaymentMethodTypes.CARD, Currency.USD);

        cardSlot.insertMoney(
                PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.CARD, Currency.USD, 500)
        );

        assertNotNull(cardSlot.getInUseCard());

        assertEquals(500.0, cardSlot.getTotal(), 0.0);

        assertThrows(CardSlotAlreadyInUseException.class, () -> {
            cardSlot.insertMoney(
                    PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.CARD, Currency.USD, 10)
            );
        });

        cardSlot.clear();

        assertEquals(0.0, cardSlot.getTotal(), 0.0);

        assertNull(cardSlot.getInUseCard());

        assertThrows(SlotSupportException.class, () -> {
            cardSlot.insertMoney(
                    PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.CARD, Currency.NIS, 10)
            );
        });
    }
}
