package com.blackjack.java.blackjack;

import com.blackjack.java.blackjack.controllers.GameController;
import com.blackjack.java.blackjack.dto.CreateGameRequestDTO;
import com.blackjack.java.blackjack.dto.GameResponseDTO;
import com.blackjack.java.blackjack.dto.PlayerDTO;
import com.blackjack.java.blackjack.service.GameService;
import com.blackjack.java.blackjack.utils.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
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

    private GameResponseDTO sampleResponse;
    private CreateGameRequestDTO createGameRequest;
    private PlayerDTO playerTest;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToController(gameController)
                .configureClient()
                .baseUrl("/game")
                .build();

        playerTest = new PlayerDTO(78L, "Peter", 250);
        sampleResponse = new GameResponseDTO(
                2L,
                playerTest,
                List.of(),
                List.of(),
                GameStatus.IN_PROGRESS,
                true,
                34
        );

        createGameRequest = new CreateGameRequestDTO(78L, 75);
    }

    @Test
    void createGame_Returns201AndBody() {
        Mockito.when(gameServiceMock.createGame(anyLong(), anyInt()))
                .thenReturn(Mono.just(sampleResponse));
        WebTestClient.ResponseSpec response = client.post()
                .uri("/new/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createGameRequest)
                .exchange();
        response.expectStatus().isEqualTo(HttpStatus.CREATED.value())
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.gameId").isEqualTo(sampleResponse.getGameId())
                .jsonPath("$.player.id").isEqualTo(playerTest.getPlayerId())
                .jsonPath("$.status").isEqualTo(sampleResponse.getStatus().getStatusName());
    }

    @Test
    void createGame_InvalidRequest_Returns400() {
        // Add test for invalid request
    }
}