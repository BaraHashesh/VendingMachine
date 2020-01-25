package payment_system;

import enums.Currency;
import enums.MachineType;
import enums.PaymentMethodTypes;
import exceptions.NotEnoughMoneyException;
import org.junit.Test;
import payment_method.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentSystemTest {

    @Test
    public void paymentSystemTest() {
        SnackVendingMachinePaymentSystem paymentSystem = (SnackVendingMachinePaymentSystem) PaymentSystemFactory
                .getInstance().buildPaymentSystem(MachineType.SNACK_VENDING_MACHINE, Currency.USD);

        paymentSystem.insertCard(
                (Card) PaymentMethodFactory.getInstance().createMoney(
                        PaymentMethodTypes.CARD,
                        Currency.USD,
                        50
                )
        );

        assertThrows(NotEnoughMoneyException.class, () -> {
           paymentSystem.buy(100);
        });

        for (int i = 0; i < 6; i++)
            paymentSystem.insertNote(
                    (Note) PaymentMethodFactory.getInstance().createMoney(
                            PaymentMethodTypes.NOTE,
                            Currency.USD,
                            20
                    )
            );

        for (int i = 0; i < 17; i++)
            paymentSystem.insertCoin(
                    (Coin) PaymentMethodFactory.getInstance().createMoney(
                            PaymentMethodTypes.COIN,
                            Currency.USD,
                            0.1
                    )
            );

        ArrayList<PaymentMethod> change = paymentSystem.buy(20);

        assertEquals(7, change.size());

        assertEquals(change.get(0).getValue(), 50.0, 0.0);
        assertEquals(change.get(0).getClass(), Note.class);
        assertEquals(change.get(1).getValue(), 50.0, 0.0);
        assertEquals(change.get(1).getClass(), Note.class);
        assertEquals(change.get(2).getValue(), 20.0, 0.0);
        assertEquals(change.get(2).getClass(), Note.class);
        assertEquals(change.get(3).getValue(), 1.0, 0.0);
        assertEquals(change.get(3).getClass(), Coin.class);
        assertEquals(change.get(4).getValue(), 0.5, 0.0);
        assertEquals(change.get(4).getClass(), Coin.class);
        assertEquals(change.get(5).getValue(), 0.2, 0.0);
        assertEquals(change.get(5).getClass(), Coin.class);
        assertEquals(change.get(6).getValue(), 30.0, 0.0);
        assertEquals(change.get(6).getClass(), Card.class);

        assertEquals(0.0, paymentSystem.getInserted(), 0.0);

        assertArrayEquals(new ArrayList<PaymentSystem>().toArray(), paymentSystem.refundAll().toArray());

        ArrayList<PaymentMethod> paymentMethodArrayList = new ArrayList<>();

        paymentMethodArrayList.add(
                PaymentMethodFactory.getInstance().createMoney(
                    PaymentMethodTypes.CARD,
                    Currency.USD,
                    50
                )
        );

        paymentSystem.insertCard((Card)paymentMethodArrayList.get(0));

        for (int i = 0; i < 6; i++) {
            paymentMethodArrayList.add(
                    PaymentMethodFactory.getInstance().createMoney(
                            PaymentMethodTypes.NOTE,
                            Currency.USD,
                            20
                    )
            );

            paymentSystem.insertNote((Note)paymentMethodArrayList.get(1 + i));
        }

        for (int i = 0; i < 17; i++) {
            paymentMethodArrayList.add(
                    PaymentMethodFactory.getInstance().createMoney(
                            PaymentMethodTypes.COIN,
                            Currency.USD,
                            0.1
                    )
            );

            paymentSystem.insertCoin((Coin) paymentMethodArrayList.get(7 + i));
        }

        assertEquals(171.7, paymentSystem.getInserted(), 0.001);

        assertArrayEquals(paymentMethodArrayList.toArray(), paymentSystem.refundAll().toArray());

        assertEquals(paymentSystem.getInserted(), 0, 0.001);
    }
}
