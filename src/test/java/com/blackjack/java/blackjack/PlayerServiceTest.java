package com.blackjack.java.blackjack;

import com.blackjack.java.blackjack.exceptions.custom.PlayerNotFoundException;
import com.blackjack.java.blackjack.repository.PlayerRepository;
import com.blackjack.java.blackjack.model.Player;
import com.blackjack.java.blackjack.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepositoryMock;

    @InjectMocks
    private PlayerService playerServiceMock;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(1L, "Peter", 500);
    }

    @Test
    void testGetPlayerById_whenExists() {
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.just(player));
        StepVerifier.create(playerServiceMock.getPlayerById(1L))
                .expectNext(player)
                .verifyComplete();
    }

    @Test
    void testErrorGettingPlayerById() {
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(playerServiceMock.getPlayerById(1L))
                .expectError(PlayerNotFoundException.class)
                .verify();
    }

    @Test
    void testCreatePlayer() {
        when(playerRepositoryMock.save(any(Player.class))).thenReturn(Mono.just(player));
        StepVerifier.create(playerServiceMock.createPlayer(player))
                .expectNextMatches(p -> p.equals(player))
                .verifyComplete();
    }

    @Test
    void testUpdatePlayer_whenExists() {
        Player updatedPlayer = new Player(1L, "Peter", 1000);
        StepVerifier.create(playerServiceMock.updatePlayer(1L, updatedPlayer));
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.just(player));
        when(playerRepositoryMock.save(any(Player.class))).thenReturn(Mono.just(updatedPlayer));
        StepVerifier.create(playerServiceMock.updatePlayer(1L, updatedPlayer))
                .expectNextMatches(p -> p.getPlayerName().equals("Peter") && p.getTotalPoints() == 1000)
                .verifyComplete();
    }

    @Test
    void testUpdatePlayer_whenNotExists() {
        Player updatedPlayer = new Player(1L, "Peter", 500);
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.empty());
        StepVerifier.create(playerServiceMock.updatePlayer(1L, updatedPlayer))
                .expectError(PlayerNotFoundException.class)
                .verify();
    }

    @Test
    void testDeletePlayer_whenExists() {
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.just(player));
        when(playerRepositoryMock.deleteById(String.valueOf(1L)).thenReturn(Mono.empty()));
        StepVerifier.create(playerServiceMock.deleteByPlayerId(1L))
                .verifyComplete();
    }

    @Test
    void testDeletePlayer_whenNotExists() {
        when(playerRepositoryMock.findById(1L)).thenReturn(Mono.empty());
        when(playerRepositoryMock.deleteById(1L).thenReturn(Mono.empty());
        StepVerifier.create(playerServiceMock.deleteByPlayerId(1L))
                .expectError(PlayerNotFoundException.class)
                .verify();
    }
}



