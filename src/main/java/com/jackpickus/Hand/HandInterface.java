package com.jackpickus.Hand;

public interface HandInterface {
    
    int getHandTotal();

    void removeCard(int i);

    boolean hasBlackJack();

    boolean hasOneAce();

    boolean busted();

}
