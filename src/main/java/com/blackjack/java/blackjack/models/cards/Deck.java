package com.blackjack.java.blackjack.models.cards;

import com.blackjack.java.blackjack.actions.Shuffle;
import com.blackjack.java.blackjack.utils.CardRank;
import com.blackjack.java.blackjack.utils.CardSuit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Deck {
    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        Shuffle.shuffle(this);
    }

    private void initializeDeck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                int value = rank.getValue();
                cards.add(new Card(suit, rank, value));
            }
        }
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("There are no more cards in the deck.");
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
