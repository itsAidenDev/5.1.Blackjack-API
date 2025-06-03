package com.blackjack.java.blackjack.service;

import com.blackjack.java.blackjack.models.Player;
import com.blackjack.java.blackjack.repositories.PlayerRepository;
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

    public Mono<Player> updatePlayerName(Long playerId, Player newPlayerName) {
        return playerRepository.findById(String.valueOf(playerId))
                .flatMap(player -> {
                    player.setPlayerName(String.valueOf(newPlayerName));
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

    public Mono<Player> getPlayerById(String playerId) {
        return playerRepository.findById(playerId);
    }

    public Mono<Void> deleteByPlayerId(Long id) {
        return playerRepository.deleteById(String.valueOf(id));
    }
}
