package com.jackpickus.Hand;

import java.util.ArrayList;
import java.util.List;

import com.jackpickus.Card.Card;

public class Hand implements HandInterface{
    
    List<Card> hand;

    public Hand(Card card1, Card card2) {
        this.hand = new ArrayList<>();
        this.hand.add(card1);
        this.hand.add(card2);
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean busted() {
        int handTotal = ((Hand) this.hand).getHandTotal();
        if (handTotal > 21) {
            System.out.println("Busted!");
            return true;
        }
        return false;
    }

}
