package com.blackjack.java.blackjack.exceptions.custom;

import lombok.Getter;

@Getter
public class PlayerNotFoundException extends RuntimeException {
    private final Long playerId;

    public PlayerNotFoundException(String playerId) {
        super("Player not found: " + playerId);
        this.playerId = playerId;
    }

}

