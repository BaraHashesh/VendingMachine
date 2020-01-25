package payment_system;

import enums.Currency;
import enums.Denomination;
import enums.PaymentMethodTypes;
import exceptions.NotEnoughMoneyException;
import payment_method.*;
import slot.CardSlot;
import slot.CoinSlot;
import slot.NoteSlot;
import slot.SlotFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SnackVendingMachinePaymentSystem extends PaymentSystem {

    private CoinSlot coinSlot;
    private NoteSlot noteSlot;
    private CardSlot cardSlot;
    private ArrayList<Denomination> acceptableDenomination;

    public SnackVendingMachinePaymentSystem(Currency currency) {
        this.coinSlot = (CoinSlot) SlotFactory.getInstance().buildSlot(PaymentMethodTypes.COIN, currency);
        this.noteSlot = (NoteSlot) SlotFactory.getInstance().buildSlot(PaymentMethodTypes.NOTE, currency);
        this.cardSlot = (CardSlot) SlotFactory.getInstance().buildSlot(PaymentMethodTypes.CARD, currency);
        this.acceptableDenomination = new ArrayList<>();

        this.acceptableDenomination.addAll(Arrays.asList(this.coinSlot.getAcceptableDenominationList()));
        this.acceptableDenomination.addAll(Arrays.asList(this.noteSlot.getAcceptableDenominationList()));

        // sort the acceptable denominations from higher to lower
        this.acceptableDenomination.sort((Denomination o1, Denomination o2) -> {
            // Check if the first object is higher than the second
            if (o1.getValue() >= o2.getValue())
                return -1;

            return 1;
        });

        this.denominationMap = new HashMap<>();

        // by default the change system is full
        this.fillChangeSystem(100);
    }

    /**
     * This function is used to fill the change for the vendingMachine
     *
     * @param copiesOfEachDenomination is the number of copies to be inside the
     *                                 change system of each denomination type
     */
    public void fillChangeSystem(int copiesOfEachDenomination) {
        // go through every denomination
        for (Denomination denomination : this.acceptableDenomination) {
            this.denominationMap.put(denomination, copiesOfEachDenomination);
        }
    }

    public void insertCoin(Coin coin) {
        this.coinSlot.insertMoney(coin);
        this.setInserted(this.getInserted() + coin.getValue());
    }

    public void insertCard(Card card) {
        this.cardSlot.insertMoney(card);
        this.setInserted(this.getInserted() + card.getValue());
    }

    public void insertNote(Note note) {
        this.noteSlot.insertMoney(note);
        this.setInserted(this.getInserted() + note.getValue());
    }

    public ArrayList<PaymentMethod> refundCoins() {
        this.setInserted(this.getInserted() - this.coinSlot.getTotal());
        return this.coinSlot.refund();
    }

    public ArrayList<PaymentMethod> refundNotes() {
        this.setInserted(this.getInserted() - this.noteSlot.getTotal());
        return this.noteSlot.refund();
    }

    public Card refundCard() {
        this.setInserted(this.getInserted() - this.cardSlot.getTotal());

        if (this.cardSlot.getInsertedPaymentMethods().size() > 0) {
            return (Card) this.cardSlot.refund().get(0);
        }

        return null;
    }

    public ArrayList<PaymentMethod> refundAll() {
        ArrayList<PaymentMethod> tmp = new ArrayList<>();

        if (this.cardSlot.getInUseCard() != null)
            tmp.add(this.refundCard());

        tmp.addAll(this.refundNotes());
        tmp.addAll(this.refundCoins());

        return tmp;
    }

    /**
     * This function is written with 2 assumptions
     * 1- The machine will always have higher than 0 copies of each denomination
     * (will never run out of a certain Denomination)
     * 2- The machine will give out higher denomination first
     *
     * @param value The value of change to be given back to the customer
     * @return An array list of PaymentMethod objects
     */
    private ArrayList<PaymentMethod> giveChange(double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.HALF_UP);

        Card card = this.cardSlot.getInUseCard();
        int i = 0, j, numDenomination = 0;

        this.cardSlot.clear();
        this.coinSlot.clear();
        this.noteSlot.clear();
        this.setInserted(0);

        // TODO: implement logic to return the change to the user
        //          while considering the possibility of no change of certain denomination

        ArrayList<PaymentMethod> change = new ArrayList<>();

        while (value > 0) {

            if (value >= this.acceptableDenomination.get(i).getValue()) {
                numDenomination = (int) (value / this.acceptableDenomination.get(i).getValue());
                value -= ((double) numDenomination * this.acceptableDenomination.get(i).getValue());
                value = Double.parseDouble(df.format(value));
            } else {
                numDenomination = 0;
            }

            // update the amount of change we have
            this.denominationMap.put(this.acceptableDenomination.get(i),
                    this.denominationMap.get(this.acceptableDenomination.get(i)) - numDenomination);

            for (j = 0; j < numDenomination; j++) {
                // check if the current denomination is a note
                if (this.noteSlot.IsAcceptableDomination(
                        PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE,
                                this.noteSlot.getBasicAcceptablePaymentMethod().getCurrency(),
                                this.acceptableDenomination.get(i).getValue())
                )) {
                    change.add(
                            PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.NOTE,
                                    this.noteSlot.getBasicAcceptablePaymentMethod().getCurrency(),
                                    this.acceptableDenomination.get(i).getValue())
                    );
                } else {
                    change.add(
                            PaymentMethodFactory.getInstance().createMoney(PaymentMethodTypes.COIN,
                                    this.coinSlot.getBasicAcceptablePaymentMethod().getCurrency(),
                                    this.acceptableDenomination.get(i).getValue())
                    );
                }
            }

            i++;
        }

        if (card != null)
            change.add(card);

        return change;
    }

    public ArrayList<PaymentMethod> buy(double price) {
        double change, tmp, originalPrice = price;

        // check if user has enough money to buy the item
        if (this.getInserted() > price) {
            // money is taken first from the card
            if (this.cardSlot.getInUseCard() != null) {
                // check if the card has enough money to buy the item
                if (this.cardSlot.getInUseCard().haveEnoughBalance(price)) {
                    this.setInserted(this.getInserted() - price);
                    this.cardSlot.getInUseCard().pay(price);
                    price = 0;
                }
                // if card doesn't have enough money
                // first all money from the card is subtracted
                else {
                    tmp = this.cardSlot.getTotal();
                    this.cardSlot.getInUseCard().pay(price);
                    this.setInserted(this.getInserted() - tmp);
                    price -= tmp;
                }
            }

            change = this.getInserted() - price;

            if (this.cardSlot.getInUseCard() != null)
                change -= this.cardSlot.getInUseCard().getValue();

            return giveChange(change);
        } else {
            throw new NotEnoughMoneyException(String.format("The selected item needs %.02f %s, but %.02f was inserted",
                    originalPrice,
                    this.cardSlot.getBasicAcceptablePaymentMethod().getCurrency().name(), getInserted())
            );
        }
    }
}
