package com.blackjack.java.blackjack.service;

import com.blackjack.java.blackjack.dto.GameResponseDTO;
import com.blackjack.java.blackjack.models.Game;
import com.blackjack.java.blackjack.models.Player;
import com.blackjack.java.blackjack.repositories.GameRepository;
import com.blackjack.java.blackjack.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
//import java.util.Date;

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
                    newGame.setPlayerId(String.valueOf(player.getPlayerId()));
                    //newGame.setCreatedAt(new Date());
                    //newGame.setStatus("IN_PROGRESS");
                    return gameRepository.save(newGame);
                });
    }

    Mono<GameResponseDTO> playerHit(String gameId);

    Mono<GameResponseDTO> playerStand(String gameId);

    public Object getGameById(Long id) {
        return null;
    }

    public Object getPlayerById(Long id) {
        return null;
    }

    public Mono<Game> playGame(Long id, String action) {
        return gameRepository.findById(id)
                .flatMap(Mono::just);
    }

    public Mono<Object> deleteGame(Long id) {
        return null;
    }
}