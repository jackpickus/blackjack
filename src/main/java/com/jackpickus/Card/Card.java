package com.jackpickus.Card;

import com.jackpickus.Suit.Suit;

public class Card {
    private String name;
    private int value;
    private Suit suit;

    public Card(String name, int value, Suit suit) {
        this.name = name;
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " of " + suit;
    }
}
