package slot;

import enums.Currency;
import enums.PaymentMethodTypes;
import exceptions.NotSupportedException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SlotFactoryTest {

    @Test
    public void slotFactoryTest() {
        Slot slot = SlotFactory.getInstance().buildSlot(PaymentMethodTypes.COIN, Currency.USD);

        assertEquals(CoinSlot.class, slot.getClass());

        slot = SlotFactory.getInstance().buildSlot(PaymentMethodTypes.CARD, Currency.USD);

        assertEquals(CardSlot.class, slot.getClass());

        slot = SlotFactory.getInstance().buildSlot(PaymentMethodTypes.NOTE, Currency.USD);

        assertEquals(NoteSlot.class, slot.getClass());

        assertThrows(NotSupportedException.class, ()-> {
            SlotFactory.getInstance().buildSlot(PaymentMethodTypes.NOTE, Currency.NIS);
        });

        assertThrows(NotSupportedException.class, ()-> {
            SlotFactory.getInstance().buildSlot(PaymentMethodTypes.BIT_COIN, Currency.USD);
        });
    }
}
