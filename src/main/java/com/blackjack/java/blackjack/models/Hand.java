package com.blackjack.java.blackjack.models;

import com.blackjack.java.blackjack.models.cards.Card;
import com.blackjack.java.blackjack.utils.CardRank;
import lombok.Data;

import java.util.*;

@Data
public class Hand {
    private List<Card> cards;
    private boolean stood;

    public Hand(){
        this.cards = new ArrayList<>();
        this.stood = false;
    }
    public void addCard(Card card){
        cards.add(card);
    }
    public int calculateValue(){
        int total = 0;
        int aceCount = 0;

        for (Card card : cards){
            total += card.getValue();
            if(card.getRank() == CardRank.ACE){
                aceCount++;
            }
        }
        while (total > 21 && aceCount >0){
            total -=10;
            aceCount--;
        }
        return total;
    }

    public boolean isBusted(){
        return calculateValue() >21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && calculateValue() == 21;
    }

    public boolean hasTwentyOne() {
        return calculateValue() == 21;
    }

    public void stand(){
        this.stood = true;
    }

    public boolean hasStood(){
        return stood;
    }

    public boolean canHit(){
        return !stood && !isBusted() && !isBlackjack();
    }
    public void clear(){
        cards.clear();
        stood = false;
    }
}
