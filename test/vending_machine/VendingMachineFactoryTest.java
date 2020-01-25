package vending_machine;


import enums.Currency;
import enums.MachineType;
import exceptions.NotSupportedException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VendingMachineFactoryTest {

    @Test
    public void vendingMachineFactoryTest() {

        VendingMachine vendingMachine = VendingMachineFactory.getInstance().buildVendingMachine(
                MachineType.SNACK_VENDING_MACHINE,
                Currency.USD,
                10
        );

        assertEquals(SnackVendingMachine.class, vendingMachine.getClass());

        assertThrows(
                NotSupportedException.class,
                () -> {
                    VendingMachineFactory.getInstance().buildVendingMachine(
                            MachineType.SNACK_VENDING_MACHINE,
                            Currency.NIS,
                            10
                    );
                }
        );

        assertThrows(
                NotSupportedException.class,
                () -> {
                    VendingMachineFactory.getInstance().buildVendingMachine(
                            MachineType.UNKNOWN_VENDING_MACHINE,
                            Currency.USD,
                            10
                    );
                }
        );
    }

}
