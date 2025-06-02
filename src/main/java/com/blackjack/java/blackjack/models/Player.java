package com.blackjack.java.blackjack.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Document(collection = "players")
public class Player {
    @Id
    private Long playerId;
    private String playerName;
    private int totalWins;
    private int totalGames;
    private int totalLosses;
    private int totalDraws;

    public Player(String name) {
        this.playerName = playerName;
        this.totalWins = 0;
        this.totalGames = 0;
    }

    @Column("balance")
    @JsonProperty("score")
    private Integer balance = 100;
}
