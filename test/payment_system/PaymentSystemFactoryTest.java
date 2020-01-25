package payment_system;

import enums.Currency;
import enums.MachineType;
import exceptions.NotSupportedException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentSystemFactoryTest {

    @Test
    public void paymentSystemFactoryTest() {
        PaymentSystem paymentSystem = PaymentSystemFactory.getInstance().buildPaymentSystem(
                MachineType.SNACK_VENDING_MACHINE, Currency.USD
        );

        assertEquals(SnackVendingMachinePaymentSystem.class, paymentSystem.getClass());

        assertThrows(NotSupportedException.class, () -> {
            PaymentSystemFactory.getInstance().buildPaymentSystem(
                    MachineType.SNACK_VENDING_MACHINE, Currency.NIS
            );
        });

        assertThrows(NotSupportedException.class, () -> {
            PaymentSystemFactory.getInstance().buildPaymentSystem(
                    MachineType.UNKNOWN_VENDING_MACHINE, Currency.USD
            );
        });
    }
}
