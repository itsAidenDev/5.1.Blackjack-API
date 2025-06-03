package com.blackjack.java.blackjack.dto;

import com.blackjack.java.blackjack.utils.GameStatus;
import com.blackjack.java.blackjack.models.Game;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

    public static GameResponseDTO toDto(Game game, PlayerDTO playerDTO) {
        List<CardDTO> playerCards = game.getPlayerHand()
                .getCards()
                .stream()
                .map(c -> new CardDTO(c.getSuit(), c.getRank()))
                .collect(Collectors.toList());

        List<CardDTO> dealerCards = game.getDealerHand()
                .getCards()
                .stream()
                .map(c -> new CardDTO(c.getSuit(), c.getRank()))
                .collect(Collectors.toList());

        if (game.isPlayerTurn() && game.getStatus() == GameStatus.IN_PROGRESS) {
            dealerCards = dealerCards.subList(0, 1);
        }

        return new GameResponseDTO(
                game.getGameId(),
                playerDTO,
                playerCards,
                dealerCards,
                game.getStatus(),
                game.isPlayerTurn(),
                game.getDeck().size()
        );
    }
}