package payment_method;

import enums.Currency;
import enums.PaymentMethodTypes;
import exceptions.NotSupportedException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentMethodFactoryTest {

    @Test
    public void paymentMethodFactoryTest() {
        PaymentMethod paymentMethod = PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.COIN, Currency.USD, 10);

        assertEquals(Coin.class, paymentMethod.getClass());
        assertEquals(Currency.USD, paymentMethod.getCurrency());
        assertEquals(10.0, paymentMethod.getValue(), 0.0);

        paymentMethod = PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE, Currency.NIS, 20);

        assertEquals(Note.class, paymentMethod.getClass());
        assertEquals(Currency.NIS, paymentMethod.getCurrency());
        assertEquals(20.0, paymentMethod.getValue(), 0.0);

        paymentMethod = PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.CARD, Currency.USD, 10);

        assertEquals(Card.class, paymentMethod.getClass());

        assertThrows(NotSupportedException.class, () -> {
            PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.BIT_COIN, Currency.USD, 10);
        });
    }
}
