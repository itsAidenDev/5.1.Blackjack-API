package com.blackjack.java.blackjack.repository;

import com.blackjack.java.blackjack.model.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends ReactiveCrudRepository<Player, String> {
    Mono<Player> findById(Long playerId);
}