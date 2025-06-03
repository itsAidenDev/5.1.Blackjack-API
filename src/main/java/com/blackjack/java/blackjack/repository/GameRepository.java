package com.blackjack.java.blackjack.repository;

import com.blackjack.java.blackjack.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    Flux<Game> findByPlayerId(Long playerId);
}