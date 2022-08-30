package com.jackpickus.Hand;

import com.jackpickus.Card.Card;

public interface HandInterface {

    boolean isBusted = false;
    
    int getHandTotal();

    void removeCard(int i);

    boolean hasBlackJack();

    boolean hasOneAce();

    boolean busted();

    void addCard(Card card);

}
