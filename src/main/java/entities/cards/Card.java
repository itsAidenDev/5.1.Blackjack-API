package entities.cards;

import lombok.*;
import utils.CardSuit;

@Data
@NoArgsConstructor
public class Card {
    private CardSuit suit;
    private String value;

    public int getValue() {
        return switch (value) {
            case "2": yield 2;
            case "3": yield 3;
            case "4": yield 4;
            case "5": yield 5;
            case "6": yield 6;
            case "7": yield 7;
            case "8": yield 8;
            case "9": yield 9;
            case "10", "J", "Q", "K": yield 10;
            case "A": yield 11;
            default: yield 0;
        };
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}