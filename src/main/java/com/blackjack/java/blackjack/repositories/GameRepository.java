package com.blackjack.java.blackjack.repositories;

import com.blackjack.java.blackjack.models.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends ReactiveCrudRepository<Game, Long> {
}