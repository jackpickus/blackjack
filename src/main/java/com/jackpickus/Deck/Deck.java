package com.jackpickus.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jackpickus.Card.Card;
import com.jackpickus.Suit.Suit;

public class Deck implements DeckInterface {
   private final String[] cardTypes = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    List<Card> deck;

    public Deck() {
        deck = makeCards();
    }

    @Override
    public List<Card> makeCards() {

        List<Card> theCards = new ArrayList<>();

        Suit diamonds = Suit.DIAMONDS;
        Suit clubs = Suit.CLUBS;
        Suit hearts = Suit.HEARTS;
        Suit spades = Suit.SPADES;

        // make a 5 card shoe
        int shoeSize = 5;
        for (int j = 0; j < shoeSize; j++) {
            for (int i = 0; i < cardTypes.length; i++) {
                Card c1, c2, c3, c4;
                if (cardTypes[i].contains("J") || cardTypes[i].contains("Q") || cardTypes[i].contains("K")) {
                    c1 = new Card(cardTypes[i], 10, diamonds);
                    c2 = new Card(cardTypes[i], 10, clubs);
                    c3 = new Card(cardTypes[i], 10, hearts);
                    c4 = new Card(cardTypes[i], 10, spades);
                } else {
                    c1 = new Card(cardTypes[i], i + 1, diamonds);
                    c2 = new Card(cardTypes[i], i + 1, clubs);
                    c3 = new Card(cardTypes[i], i + 1, hearts);
                    c4 = new Card(cardTypes[i], i + 1, spades);
                }
                theCards.add(c1);
                theCards.add(c2);
                theCards.add(c3);
                theCards.add(c4);
            }
        }
        return theCards;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    @Override
    public Card dealCard() {
        return this.deck.remove(0);
    }

    @Override
    public Integer cardsLeft() {
        return this.deck.size();
    } 
}
