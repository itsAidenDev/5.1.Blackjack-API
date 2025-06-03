package com.blackjack.java.blackjack.controllers;

import com.blackjack.java.blackjack.dto.GameMapper;
import com.blackjack.java.blackjack.dto.GameResponse;
import com.blackjack.java.blackjack.dto.PlayerDTO;
import com.blackjack.java.blackjack.dto.RankingDTO;
import com.blackjack.java.blackjack.model.Game;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.blackjack.java.blackjack.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag (name = "Game API", description = "Game Management Endpoints")
@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Create new game", description = "Create new game with a player and a bet")
    @ApiResponse(responseCode = "201", description = "NEW GAME CREATED")
    @PostMapping("/new/{playerId}")
    public Mono<ResponseEntity<GameResponse>> createNewGame(@PathVariable Long playerId, @RequestBody int bet) {
        return gameService.createGame(playerId, bet)
                .map(game -> ResponseEntity.status(HttpStatus.CREATED).body(game));
    }

    @Operation(summary = "Get game by ID", description = "Gets the chosen game by ID")
    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<Object>> getGameById(@PathVariable Long id) {
        return Mono.justOrEmpty(gameService.getGameById(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Play game by ID", description = "Plays the chosen game by ID")
    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<Game>> playGame(
            @PathVariable Long gameId,
            @RequestBody String action) {
        return gameService.playGame(gameId, action)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete game by ID", description = "Deletes the chosen game by ID")
    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Object>> deleteGame(@PathVariable Long gameId) {
        return gameService.deleteGame(gameId)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @Operation(summary = "Get all games", description = "Gets all existing games")
    @GetMapping
    public Mono<ResponseEntity<Flux<GameResponse>>> getAllGames() {
        return gameService.getAllGames()
                .flatMap(game -> Mono.just(GameMapper.toDto(game, new PlayerDTO())))
                .collectList()
                .map(games -> games.isEmpty()
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.ok().body(Flux.fromIterable(games))
                );
    }

    @Operation(summary = "Get player ranking")
    @GetMapping("/ranking")
    public Flux<RankingDTO> getPlayerRanking() {
        return gameService.getPlayerRanking();
    }

    @Operation(summary = "The player gets a card")
    @PostMapping("/{gameId}/play/hit")
    public Mono<GameResponse> playerHit(@PathVariable Long gameId) {
        return gameService.playerHit(gameId);
    }

    @Operation(summary = "The player stands")
    @PostMapping("/{gameId}/play/stand")
    public Mono<GameResponse> playerStand(@PathVariable Long gameId) {
        return gameService.playerStand(gameId);
    }

}