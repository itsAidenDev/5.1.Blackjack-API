package service;

import entities.gameSessions.Game;
import entities.Player;
import repositories.mysql.GameRepository;
import repositories.mongodb.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.net.http.HttpHeaders;
import java.util.Date;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public Mono<Game> createNewGame(String playerName) {
        return playerRepository.findById(playerName)
                .switchIfEmpty(Mono.defer(() -> {
                    Player newPlayer = new Player(playerName);
                    return playerRepository.save(newPlayer);
                }))
                .flatMap(player -> {
                    Game newGame = new Game();
                    newGame.setPlayerId(player.getId());
                    newGame.setCreatedAt(new Date());
                    newGame.setStatus("IN_PROGRESS");
                    return Mono.just(gameRepository.save(newGame));
                });
    }

    public Object getGameById(Long id) {
        return null;
    }

    public Object getPlayerById(Long id) {
        return null;
    }

    public HttpHeaders playGame(Long id, String action) {
        return null;
    }

    public Mono<Object> deleteGame(Long id) {
        return null;
    }
}