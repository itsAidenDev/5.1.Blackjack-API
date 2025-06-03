package com.blackjack.java.blackjack.utils;

public enum CardSuit {
    SPADES("Spades"),
    CLUBS("Clubs"),
    HEARTS("Hearts"),
    DIAMONDS("Diamonds");

    private final String suitName;

    CardSuit(String suitName) {
        this.suitName = suitName;
    }

    public String getSuitName() {
        return suitName;
    }

    @Override
    public String toString() {
        return suitName;
    }
}