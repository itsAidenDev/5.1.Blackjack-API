package com.blackjack.java.blackjack.service;

import com.blackjack.java.blackjack.dto.GameMapper;
import com.blackjack.java.blackjack.dto.GameResponseDTO;
import com.blackjack.java.blackjack.dto.PlayerDTO;
import com.blackjack.java.blackjack.exceptions.InvalidPlayException;
import com.blackjack.java.blackjack.exceptions.PlayerNotFoundException;
import com.blackjack.java.blackjack.models.Game;
import com.blackjack.java.blackjack.models.Player;
import com.blackjack.java.blackjack.repositories.GameRepository;
import com.blackjack.java.blackjack.repositories.PlayerRepository;
import com.blackjack.java.blackjack.utils.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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
    public Mono<GameResponseDTO> createGame(Long playerId, int bet) {
        return playerRepository.findById(String.valueOf(playerId))
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(playerId)))
                .flatMap(player -> {
                    if (player.getTotalPoints() == null || player.getTotalPoints() <= 0) {
                        throw new InvalidPlayException("The player " + player.getPlayerName() + "does not have enough points: " + player.getTotalPoints());
                    }
                    if (bet <= 0) {
                        throw new InvalidPlayException("The bet cannot be less than 0");
                    }
                    if (bet > player.getTotalPoints()) {
                        throw new InvalidPlayException("The bet cannot be greater than the player points: " + player.getTotalPoints());
                    }
                    Game game = new Game(player.getPlayerId(), bet);
                    game.dealInitialCards();
                    game.checkInitialBlackjack();
                    return gameRepository.save(game)
                            .flatMap(savedGame -> {
                                Mono<Game> gameMono;
                                if (savedGame.getStatus() != GameStatus.IN_PROGRESS) {
                                    gameMono = settleBet(savedGame).thenReturn(savedGame);
                                } else {
                                    gameMono = Mono.just(savedGame);
                                }
                                return gameMono
                                        .flatMap(gameResult ->
                                                playerRepository.findById(gameResult.getPlayerId())
                                                        .switchIfEmpty(Mono.error(new PlayerNotFoundException(gameResult.getPlayerId())))
                                                        .map(playerFound -> GameMapper.toDto(
                                                                gameResult,
                                                                new PlayerDTO(playerFound.getPlayerId(), playerFound.getPlayerName(), playerFound.getTotalPoints())
                                                        ))
                                        );
                            });
                });
    }

    Mono<GameResponseDTO> playerHit(String gameId) {
        return gameRepository.findById(Long.valueOf(gameId))
                .flatMap(game -> {
                    if (game.getStatus() != GameStatus.IN_PROGRESS) {
                        return Mono.error(new InvalidPlayException("Game is not in progress"));
                    }
                    game.playerHit();
                    return gameRepository.save(game)
                            .flatMap(savedGame -> {
                                return Mono.just(GameMapper.toDto(savedGame, new PlayerDTO()));
                            });
                });
    }

    Mono<GameResponseDTO> playerStand(String gameId) {
        return gameRepository.findById(Long.valueOf(gameId))
                .flatMap(game -> {
                    if (game.getStatus() != GameStatus.IN_PROGRESS) {
                        return Mono.error(new InvalidPlayException("Game is not in progress"));
                    }
                    game.playerStand();
                    return gameRepository.save(game)
                            .flatMap(savedGame -> {
                                return Mono.just(GameMapper.toDto(savedGame, new PlayerDTO()));
                            });
                });
    }
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