package com.blackjack.java.blackjack.exceptions;

import lombok.Getter;

@Getter
public class PlayerNotFoundException extends RuntimeException {
    private final Long playerId;

    public PlayerNotFoundException(Long playerId) {
        super("Player not found: " + playerId);
        this.playerId = playerId;
    }

}

