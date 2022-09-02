package com.jackpickus.Hand;

import java.util.ArrayList;
import java.util.List;

import com.jackpickus.Card.Card;

public class Hand implements HandInterface{
    
    List<Card> hand;
    private boolean isBusted;
    private boolean hasOneAce;
    private int handAceTotal;
    private int handTotal;

    public Hand(Card card1, Card card2) {
        this.hand = new ArrayList<>();
        this.hand.add(card1);
        this.hand.add(card2);
        handTotal = card1.getValue() + card2.getValue();
        handAceTotal = handTotal + 10;
        isBusted = false;
        hasOneAce = false;
    }

    public int getHandTotal() {
        return handTotal;
    }

    @Override
    public void removeCard(int index) {
        Card c = this.hand.remove(index);
        this.handTotal -= c.getValue();
        this.handAceTotal -= c.getValue();
    }

    @Override
    public boolean hasBlackJack() {

        return (this.hand.get(0).getName().equals("Ace") || this.hand.get(1).getName().equals("Ace")) &&
                (this.hand.get(0).getValue() == 10 || this.hand.get(1).getValue() == 10);
    }

    @Override
    public boolean hasOneAce(Card card1, Card card2) {
        if (card1.getName().equals("Ace")) {
            setHasOneAce(true);
            return true;
        } else if (card2.getName().equals("Ace")) {
            setHasOneAce(true);
            return true;
        } else {
            return false;
        }
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
        this.hand.add(card);
        this.handTotal += card.getValue();
        this.handAceTotal += card.getValue();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Player hand: ");
        for (Card c : this.hand) {
            s.append(c.toString()).append(" ");

        }
        s.append("\n");
        int handTotal;
        if (!hasOneAce){
            handTotal = getHandTotal();
        }else {
            handTotal = this.getHandAceTotal();
        }
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

    public void setHandAceTotal(int handAceTotal) {
        this.handAceTotal = handAceTotal;
    }

    public boolean getHasOneAce() {
        return hasOneAce;
    }

    public void setHasOneAce(boolean hasOneAce) {
        this.hasOneAce = hasOneAce;
    }
}
