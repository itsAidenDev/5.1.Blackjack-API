package entities.cards;

import lombok.*;
import utils.CardSuit;
import utils.CardValue;

@Data
@Getter
@Setter
@NoArgsConstructor
public class Card {
    private CardSuit suit;
    private int value;
    private String rank;

    public Card(String suit, String rank, int value) {
        this.suit = CardSuit.valueOf(suit);
        this.rank = rank;
        this.value = value;
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
        return value + " of " + suit;
    }

}
