package payment_method;

import enums.Currency;
import enums.PaymentMethodTypes;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void cardTest() {
        Card card = (Card) PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.CARD,
                Currency.USD, 30);

        assertTrue(card.haveEnoughBalance(10.0));
        assertFalse(card.haveEnoughBalance(31.0));

        card.pay(15.0);

        assertEquals(15.0, card.getValue(), 0.0);
    }
}
