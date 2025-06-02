import entities.gameSessions.Game;
import entities.Player;
import service.GameService;
import repositories.mysql.GameRepository;
import repositories.mongodb.PlayerRepository;
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
        newPlayer.setId("player123");

        Game newGame = new Game();
        newGame.setId(1L);
        newGame.setPlayerId(newPlayer.getId());

        when(playerRepository.findByName(playerName)).thenReturn(Mono.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(newPlayer));
        when(gameRepository.save(any(Game.class))).thenReturn(newGame);

        Mono<Game> result = gameService.createNewGame(playerName);

        StepVerifier.create(result)
                .expectNextMatches(game ->
                        game.getId() == 1L &&
                                game.getPlayerId().equals("player123"))
                .verifyComplete();
    }
}