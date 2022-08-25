package com.jackpickus.Hand;

public interface HandInterface {
    
    int getHandTotal();

    void removeCard();

    boolean hasBlackJack();

    boolean hasOneAce();

    boolean busted();

}
