package com.blackjack.java.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {
    private Long playerId;
    private String playerName;
    private long totalGames;
    private long totalWins;
    private long totalLosses;
    private long totalDraws;
    private double winRate;

}
