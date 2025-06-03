package com.blackjack.java.blackjack.model.cards;

import com.blackjack.java.blackjack.actions.DrawCard;
import com.blackjack.java.blackjack.actions.Shuffle;
import com.blackjack.java.blackjack.utils.CardRank;
import com.blackjack.java.blackjack.utils.CardSuit;
import lombok.Getter;
import java.util.Queue;
import java.util.LinkedList;


@Getter
public class Deck {
    private final Queue<Card> cards  = new LinkedList<>();;
    private final DrawCard drawCardFromDeck = new DrawCard();

    public Deck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                cards.add(new Card(suit, rank, rank.getValue()));
            }
        }
        Shuffle.shuffle(cards);
    }
    public Card drawCard() {
        return cards.poll();
    }

    public int size() {
        return cards.size();
    }
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
