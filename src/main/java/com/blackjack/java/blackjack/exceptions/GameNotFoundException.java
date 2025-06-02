package com.blackjack.java.blackjack.exceptions;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String gameId) {
        super("Game not found: " + gameId);
    }
}