package com.blackjack.java.blackjack.actions;

import com.blackjack.java.blackjack.model.cards.Card;

import java.util.List;

public class DrawCard {
    private List<Card> cards;

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No more cards left in the deck.");
        } else {
            return cards.removeFirst();
        }
    }
    public int size() {
        return cards.size();
    }
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

