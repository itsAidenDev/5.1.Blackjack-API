package service;

import entities.Player;
import repositories.mongodb.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> updatePlayerName(String playerId, String newName) {
        return playerRepository.findById(playerId)
                .flatMap(player -> {
                    player.setName(newName);
                    return playerRepository.save(player);
                });
    }

    public Flux<Player> getPlayerRanking() {
        return playerRepository.findAll()
                .sort((p1, p2) -> Double.compare(
                        p2.getTotalWins() / (double) p2.getTotalGames(),
                        p1.getTotalWins() / (double) p1.getTotalGames()
                ));
    }
}
