package payment_system;


import enums.Currency;
import enums.MachineType;
import exceptions.NotSupportedException;

public class PaymentSystemFactory {

    private static final PaymentSystemFactory instance = new PaymentSystemFactory();

    public static PaymentSystemFactory getInstance() {
        return instance;
    }

    public PaymentSystem buildPaymentSystem(MachineType machineType, Currency currency) {

        switch (machineType) {
            case SNACK_VENDING_MACHINE:
                return new SnackVendingMachinePaymentSystem(currency);
            default:
                throw new NotSupportedException("%s machine is yet to be supported");
        }
    }
}
