package com.jackpickus.Deck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jackpickus.Card.Card;
import com.jackpickus.Suit.Suit;

public class DeckTest {
    
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @After
    public void tearDown() {
        deck = null;
    }

    @Test
    public void dealOneCard() {
        Card oneCard = deck.dealCard();
        assertEquals(Suit.DIAMONDS, oneCard.getSuit());
        assertEquals("Ace", oneCard.getName());
    }

    @Test
    public void cardsLeft() {
        int beginningAmount = deck.cardsLeft();
        deck.dealCard();
        deck.dealCard();
        deck.dealCard();

        int newAmountOfCards = deck.cardsLeft();

        assertEquals(beginningAmount - 3, newAmountOfCards);
    }

    @Test
    public void isDeckShuffled() {
        Card firstCard = deck.dealCard();

        deck = new Deck();
        deck.shuffle();

        Card newFirstCard = deck.dealCard();

        assertNotEquals(firstCard, newFirstCard);
    }


}
