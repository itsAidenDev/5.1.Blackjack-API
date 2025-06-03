package com.blackjack.java.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse {
    private PlayerDTO playerId;
    private String playerName;
    private int totalGames;
    private int totalWins;
    private int totalLosses;
    private int totalDraws;
    private double winRate;
}



