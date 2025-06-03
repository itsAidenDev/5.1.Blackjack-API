package com.blackjack.java.blackjack.repositories;

import com.blackjack.java.blackjack.models.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {
    Mono<Player> findByPlayerId(Long playerId);

    Mono<Player> findByName(String playerName);
}