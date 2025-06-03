package com.blackjack.java.blackjack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {
    private Long playerId;
    private String name;
    private long totalGames;
    private long totalWins;
    private long totalLosses;
    private long totalDraws;
    private Integer totalPoints;
    private double winRate;
}
