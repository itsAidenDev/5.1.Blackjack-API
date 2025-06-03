package com.blackjack.java.blackjack.service;

import com.blackjack.java.blackjack.dto.GameMapper;
import com.blackjack.java.blackjack.dto.GameResponse;
import com.blackjack.java.blackjack.dto.PlayerDTO;
import com.blackjack.java.blackjack.dto.RankingDTO;
import com.blackjack.java.blackjack.exceptions.custom.InvalidPlayException;
import com.blackjack.java.blackjack.exceptions.custom.PlayerNotFoundException;
import com.blackjack.java.blackjack.model.Game;
import com.blackjack.java.blackjack.repository.GameRepository;
import com.blackjack.java.blackjack.repository.PlayerRepository;
import com.blackjack.java.blackjack.utils.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
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
    public Mono<GameResponse> createGame(Long playerId, int bet) {
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
                                    gameMono = savedGame.settleBet(savedGame).thenReturn(savedGame);
                                } else {
                                    gameMono = Mono.just(savedGame);
                                }
                                return gameMono
                                        .flatMap(gameResult ->
                                                playerRepository.findById(gameResult.getPlayerId())
                                                        .switchIfEmpty(Mono.error(new PlayerNotFoundException(Long.parseLong(gameResult.getPlayerId()))))
                                                        .map(playerFound -> GameMapper.toDto(
                                                                gameResult,
                                                                new PlayerDTO(playerFound.getPlayerId(), playerFound.getPlayerName(), playerFound.getTotalPoints())
                                                        ))
                                        );
                            });
                });
    }

    public Mono<GameResponse> playerHit(Long gameId) {
        return gameRepository.findById(String.valueOf(gameId))
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

    public Mono<GameResponse> playerStand(Long gameId) {
        return gameRepository.findById(String.valueOf(gameId))
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

    public Flux<RankingDTO> getPlayerRanking() {
        return gameRepository.findAll()
                .filter(game -> game.getPlayerId() != null)
                .groupBy(Game::getPlayerId)
                .flatMap(groupedFlux ->
                        groupedFlux.collectList()
                                .flatMap(gamesList ->
                                        playerRepository.findById(groupedFlux.key())
                                                .map(player -> {
                                                    long totalGames = gamesList.size();
                                                    long totalWins = gamesList.stream()
                                                            .filter(game -> game.getStatus() == GameStatus.WON)
                                                            .count();
                                                    long totalLosses = gamesList.stream()
                                                            .filter(game ->
                                                                    game.getStatus() == GameStatus.LOST
                                                                            || game.getStatus() == GameStatus.BUSTED)
                                                            .count();
                                                    long totalDraws = gamesList.stream()
                                                            .filter(game -> game.getStatus() == GameStatus.DRAW)
                                                            .count();
                                                    double winRate = totalGames > 0 ? (totalWins * 100.0 / totalGames) : 0.0;
                                                    return new RankingDTO(
                                                            player.getPlayerId(),
                                                            player.getPlayerName(),
                                                            totalGames,
                                                            totalWins,
                                                            totalLosses,
                                                            totalDraws,
                                                            winRate
                                                    );
                                                })))
                .sort((r1, r2) -> Double.compare(r2.getWinRate(), r1.getWinRate()));
    }
    public Object getGameById(Long gameId) {
        return gameId;
    }

    public Object getPlayerById(Long id) {
        return null;
    }

    public Mono<Game> playGame(Long gameId, String action) {
        return gameRepository.findById(String.valueOf(gameId))
                .flatMap(Mono::just);
    }

    public Mono<Object> deleteGame(Long id) {
        return null;
    }

    public Flux<Game> getAllGames() {
        return gameRepository.findAll();
    }
}