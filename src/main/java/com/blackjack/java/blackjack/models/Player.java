package com.blackjack.java.blackjack.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("players")
public class Player {
    @Id
    private Long playerId;

    @Column("name")
    private String playerName;

    @Column("total_games")
    private int totalGames;

    @Column("total_wins")
    private int totalWins;

    @Column("total_losses")
    private int totalLosses;

    @Column("total_draws")
    private int totalDraws;

    public Player(String name) {
        this.playerName = playerName;
        this.totalWins = 0;
        this.totalGames = 0;
    }

    @Schema(description = "Total points to bet", defaultValue = "100")
    @Column("total_points")
    @JsonProperty("score")
    private Integer totalPoints = 100;
}
