package com.blackjack.java.blackjack.actions;

import com.blackjack.java.blackjack.models.cards.Deck;

import java.util.Collections;

public class Shuffle {
    public static void shuffle(Deck deck) {
        Collections.shuffle(deck.getCards());
    }
}