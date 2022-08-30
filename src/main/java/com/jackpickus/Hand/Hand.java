package com.jackpickus.Hand;

import java.util.ArrayList;
import java.util.List;

import com.jackpickus.Card.Card;

public class Hand implements HandInterface{
    
    List<Card> hand;
    private boolean isBusted;
    private boolean hasOneAce;
    private int handAceTotal;

    public Hand(Card card1, Card card2) {
        this.hand = new ArrayList<>();
        this.hand.add(card1);
        this.hand.add(card2);
        isBusted = false;
        hasOneAce = false;
    }

    @Override
    public int getHandTotal() {
        int total = 0;
        for (Card c : this.hand) {
            total += c.getValue();
        }
        return total;
    }

    @Override
    public void removeCard(int index) {
        this.hand.remove(index);
    }

    @Override
    public boolean hasBlackJack() {

        return (this.hand.get(0).getName().equals("Ace") || this.hand.get(1).getName().equals("Ace")) &&
                (this.hand.get(0).getValue() == 10 || this.hand.get(1).getValue() == 10);
    }

    @Override
    public boolean hasOneAce() {
        return hasOneAce;
    }

    @Override
    public boolean busted() {
        int handTotal = getHandTotal();
        if (handTotal > 21) {
            System.out.println("Busted!");
            return true;
        }
        return false;
    }

    @Override
    public void addCard(Card card) {
            if (card.getName().equals("Ace") && !this.hasOneAce) {
                this.hasOneAce = true;
                handAceTotal += this.getHandTotal() + 10;
            }
        this.hand.add(card);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Player hand: ");
        for (Card c : this.hand) {
            s.append(c.toString()).append(" ");

        }
        s.append("\n");
        int handTotal = getHandTotal();
        s.append("Total: ").append(handTotal);
        return s.toString();
    }

    public boolean isBusted() {
        return isBusted;
    }

    public void setBusted(boolean busted) {
        isBusted = busted;
    }

    public int getHandAceTotal() {
        return handAceTotal;
    }
}
