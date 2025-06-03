package com.blackjack.java.blackjack.model.cards;

import lombok.*;
import com.blackjack.java.blackjack.utils.CardSuit;
import com.blackjack.java.blackjack.utils.CardRank;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Card {
    private CardSuit suit;
    private CardRank rank;

    public Card(CardSuit suit, CardRank rank, int value) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue() {
        return switch (rank){
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case TEN, JACK, QUEEN, KING -> 10;
            case ACE -> 11;
        };
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
