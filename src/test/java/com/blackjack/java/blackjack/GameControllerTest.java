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
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private WebTestClient client;

    @Mock
    private GameService gameServiceMock;

    @InjectMocks
    private GameController gameController;

    private GameResponseDTO sampleResponse;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToController(gameController)
                .configureClient()
                .baseUrl("/game")
                .build();

        PlayerDTO playerTest = new PlayerDTO(78L, "Fernando", 500);
        sampleResponse = new GameResponseDTO(
                2L,
                playerTest,
                List.of(),
                List.of(),
                GameStatus.IN_PROGRESS,
                true,
                34);
    }

    @Test
    void createGame_Returns201AndBody() {
        CreateGameRequestDTO request = new CreateGameRequestDTO(1L, 100);
        Mockito.when(gameServiceMock.createGame(request.getPlayerId(), request.getBet()))
                .thenReturn(Mono.just(sampleResponse));

        client.post()
                .uri("/new/1")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(201)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.gameId").isEqualTo("gameId")
                .jsonPath("$.player.id").isEqualTo(1)
                .jsonPath("$.status").isEqualTo(GameStatus.IN_PROGRESS.toString());
    }
}