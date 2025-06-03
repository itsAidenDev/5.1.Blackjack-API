package com.blackjack.java.blackjack.service;

import com.blackjack.java.blackjack.dto.PlayerResponse;
import com.blackjack.java.blackjack.exceptions.ErrorDetails;
import com.blackjack.java.blackjack.exceptions.custom.PlayerNotFoundException;
import com.blackjack.java.blackjack.model.Player;
import com.blackjack.java.blackjack.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> createPlayer(Player player) {
        player.setPlayerId(null);
        if (player.getTotalPoints() == null) player.setTotalPoints(100);
        return playerRepository.save(player);
    }

    public Mono<Player> updatePlayer(Long playerId, Player player) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player with ID " + playerId + " not found")))
                .flatMap(existingPlayer -> {
                    existingPlayer.setPlayerName(player.getPlayerName());
                    existingPlayer.setTotalPoints(player.getTotalPoints());
                    return playerRepository.save(existingPlayer);
                });
    }

    public Flux<Player> getPlayerRanking() {
        return playerRepository.findAll()
                .sort((p1, p2) -> Double.compare(
                        p2.getTotalWins() / (double) p2.getTotalGames(),
                        p1.getTotalWins() / (double) p1.getTotalGames()
                ));
    }

    public Mono<Player> getPlayerById(Long playerId) {
        return playerRepository.findById(playerId);
    }

    public Mono<Void> deleteByPlayerId(Long id) {
        return playerRepository.deleteById(String.valueOf(id));
    }

    public Flux<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
