package com.jackpickus.Deck;

import java.util.List;

import com.jackpickus.Card.Card;

public interface DeckInterface {
    List<Card> makeCards();

    void shuffle();

    Card dealCard();

    Integer cardsLeft();
}
