package com.blackjack.java.blackjack.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGameRequestDTO {

    @NotNull(message = "Player ID is required")
    private Long playerId;

    @Positive(message = "The bet must be more than 0")
    private int bet;
}
