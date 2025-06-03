package com.blackjack.java.blackjack.actions;

import com.blackjack.java.blackjack.model.cards.Card;
import java.util.Queue;
import java.util.Random;

public class Shuffle {
    public static void shuffle(Queue<Card> cards) {
        int size = cards.size();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(size - i);
            Card card = cards.poll();
            for (int j = 0; j < index; j++) {
                card = cards.poll();
            }
            cards.add(card);

        }
    }
}