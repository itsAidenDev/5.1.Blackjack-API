package com.blackjack.java.blackjack;

import com.blackjack.java.blackjack.controllers.GameController;
import com.blackjack.java.blackjack.dto.CreateGameRequestDTO;
import com.blackjack.java.blackjack.dto.GameResponse;
import com.blackjack.java.blackjack.dto.PlayerDTO;
import com.blackjack.java.blackjack.exceptions.custom.InvalidPlayException;
import com.blackjack.java.blackjack.service.GameService;
import com.blackjack.java.blackjack.utils.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameServiceMock;

    @InjectMocks
    private GameController gameController;

    private WebTestClient client;

    private GameResponse sampleResponse;
    private CreateGameRequestDTO createGameRequest;
    private PlayerDTO playerTest;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToController(gameController)
                .configureClient()
                .baseUrl("/game")
                .build();

        playerTest = new PlayerDTO(1L, "Peter", 250);
        sampleResponse = new GameResponse(
                2L,
                playerTest,
                List.of(),
                List.of(),
                GameStatus.IN_PROGRESS,
                true,
                34
        );
    }

    @Test
    void createGame_InvalidRequest_Returns400() {
        CreateGameRequestDTO invalidRequest = new CreateGameRequestDTO(1L, -10);
        Mockito.when(gameServiceMock.createGame(anyLong(), anyInt()))
                .thenThrow(new InvalidPlayException("The bet cannot be less than 0"));

        client.post()
                .uri("/new/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.message").isEqualTo("The bet cannot be less than 0"); // Asumiendo que devuelves un JSON con campo message
    }

    @Test
    void createGame_Returns201AndBody() {
        CreateGameRequestDTO request = new CreateGameRequestDTO(1L, 100);
        Mockito.when(gameServiceMock.createGame(request.getPlayerId(), request.getBet()))
                .thenReturn(Mono.just(sampleResponse));

        client.post()
                .uri("/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.gameId").isEqualTo("gameId")
                .jsonPath("$.player.id").isEqualTo(1)
                .jsonPath("$.status").isEqualTo(GameStatus.IN_PROGRESS.name());
    }
}