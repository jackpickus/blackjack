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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasOneAce() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean busted() {
        // TODO Auto-generated method stub
        return false;
    }

}
