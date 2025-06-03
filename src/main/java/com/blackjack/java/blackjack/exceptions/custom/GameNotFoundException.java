package com.blackjack.java.blackjack.exceptions.custom;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String gameId) {
        super("Game not found: " + gameId);
    }
}