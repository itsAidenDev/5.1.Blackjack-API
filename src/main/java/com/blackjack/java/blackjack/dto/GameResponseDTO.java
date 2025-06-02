package com.blackjack.java.blackjack.dto;

import com.blackjack.java.blackjack.utils.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponseDTO {
    private String gameId;
    private PlayerDTO player;
    private List<CardDTO> playerHand;
    private List<CardDTO> dealerHand;
    private GameStatus status;
    private boolean playerTurn;
    private int remainingCards;
}
