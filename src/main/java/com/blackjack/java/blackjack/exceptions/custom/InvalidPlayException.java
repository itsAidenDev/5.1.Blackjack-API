package com.blackjack.java.blackjack.exceptions.custom;

public class InvalidPlayException extends RuntimeException {
    public InvalidPlayException(String message) {
        super(message);
    }
}
