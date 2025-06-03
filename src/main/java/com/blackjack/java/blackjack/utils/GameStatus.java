package com.blackjack.java.blackjack.utils;

public enum GameStatus {
    IN_PROGRESS("In Progress"),
    WON("Won"),
    LOST("Lost"),
    DRAW("Draw"),
    BUSTED("Busted"),
    SETTLED("Settled");

    private final String statusName;

    GameStatus(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return this.statusName;
    }
}

