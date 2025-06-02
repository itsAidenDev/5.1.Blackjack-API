package actions;

import entities.cards.Card;

public class DrawCard {
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No more cards in the deck.");
        } else {
            return cards.remove(0);
        }
    }
    public int size() {
        return cards.size();
    }
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
}
