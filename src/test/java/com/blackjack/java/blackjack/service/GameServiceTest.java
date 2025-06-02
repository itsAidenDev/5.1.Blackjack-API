package com.blackjack.java.blackjack.service;

import com.blackjack.java.blackjack.models.Game;
import com.blackjack.java.blackjack.models.Player;
import com.blackjack.java.blackjack.repositories.GameRepository;
import com.blackjack.java.blackjack.repositories.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testCreateNewGameWithNewPlayer() {
        String playerName = "testPlayer";
        Player newPlayer = new Player(playerName);
        newPlayer.setPlayerId(Long.valueOf("123"));

        Game newGame = new Game();
        newGame.setGameId(1L);
        newGame.setPlayerId(String.valueOf(newPlayer.getPlayerId()));

        when(playerRepository.findByName(playerName)).thenReturn(Mono.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(newPlayer));
        when(gameRepository.save(any(Game.class))).thenReturn(newGame);

        Mono<Game> result = gameService.createNewGame(playerName);

        StepVerifier.create(result)
                .expectNextMatches(game ->
                        game.getGameId().equals(1L) &&
                                game.getPlayerId().equals("player123"))
                .verifyComplete();
    }
}