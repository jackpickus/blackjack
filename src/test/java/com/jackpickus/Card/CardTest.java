package com.jackpickus.Card;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jackpickus.Suit.Suit;

public class CardTest {

    private Card myCard;

    @Before
    public void setUp() {
        myCard = new Card("5", 5, Suit.SPADES);
    }

    @After
    public void tearDown() {
        myCard = null;
    }
    
    @Test
    public void suitIsSpades() {
        assertEquals(Suit.SPADES, myCard.getSuit());
    }

    @Test
    public void valueIsFive() {
        assertEquals(5, myCard.getValue());
    }
}
