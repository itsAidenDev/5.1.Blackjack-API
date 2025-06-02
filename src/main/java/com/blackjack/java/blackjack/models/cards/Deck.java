package com.blackjack.java.blackjack.models.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        String[] suits = {"HEARTS", "DIAMONDS", "CLUBS", "SPADES"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String rank : ranks) {
                int value;
                if (rank.equals("A")) {
                    value = 11;
                } else if (rank.equals("J") || rank.equals("Q") || rank.equals("K")) {
                    value = 10;
                } else {
                    value = Integer.parseInt(rank);
                }
                cards.add(new Card(suit, rank, value));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.removeFirst();
    }

    public int size() {
        return cards.size();}
}
