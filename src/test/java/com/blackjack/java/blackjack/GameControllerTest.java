package com.blackjack.java.blackjack;

import controllers.GameController;
import entities.gameSessions.Game;
import service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @Test
    public void testCreateNewGame() {
        Game game = new Game();
        game.setId(1L);

        when(gameService.createNewGame(anyString()))
                .thenReturn(Mono.just(game));

        Mono<ResponseEntity<Game>> result = gameController.createNewGame("testPlayer");

        StepVerifier.create(result)
                .expectNextMatches(response ->
                {
                    if (response.getStatusCode() == HttpStatus.CREATED) {
                        response.getBody();
                    }
                    return false;
                })
                .verifyComplete();
    }
}