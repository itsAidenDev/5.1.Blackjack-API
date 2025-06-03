package com.blackjack.java.blackjack.exceptions.custom;

public class InvalidActionException extends RuntimeException {
    public InvalidActionException(String message) {
        super(message);
    }
}