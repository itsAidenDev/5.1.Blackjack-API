package com.blackjack.java.blackjack.exceptions.custom;

public class RankingIsEmptyException extends RuntimeException {
    public RankingIsEmptyException(String message) {
        super(message);
    }
}
