package com.jackpickus.Hand;

import java.util.ArrayList;
import java.util.List;

import com.jackpickus.Card.Card;

public class Hand {
    
    List<Card> hand;

    public Hand(Card card1, Card card2) {
        this.hand = new ArrayList<>();
        this.hand.add(card1);
        this.hand.add(card2);
    }

    public int getHandTotal() {
        int total = 0;
        for (Card c : this.hand) {
            total += c.getValue();
        }
        return total;
    }

}
